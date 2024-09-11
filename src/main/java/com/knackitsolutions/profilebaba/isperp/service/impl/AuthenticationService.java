package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.controller.VendorController.LoginRequest;
import com.knackitsolutions.profilebaba.isperp.dto.*;
import com.knackitsolutions.profilebaba.isperp.entity.main.*;
import com.knackitsolutions.profilebaba.isperp.enums.UserType;
import com.knackitsolutions.profilebaba.isperp.exception.*;
import com.knackitsolutions.profilebaba.isperp.repository.main.AdminUserRepository;
import com.knackitsolutions.profilebaba.isperp.repository.main.TenantRepository;
import com.knackitsolutions.profilebaba.isperp.repository.main.UserRoleFeatureRepository;
import com.knackitsolutions.profilebaba.isperp.service.OTPService;
import com.knackitsolutions.profilebaba.isperp.service.UserService;
import com.knackitsolutions.profilebaba.isperp.utility.JwtTokenUtil;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
  private final JwtTokenUtil jwtTokenUtil;

  private final JwtUserDetailsService jwtUserDetailsService;
  private final AdminUserRepository adminUserRepository;
  private final VendorService vendorService;
  private final UserService userService;
  private final UserRoleFeatureRepository userRoleFeatureRepository;
  private final TenantRepository tenantRepository;
  private final OTPService otpService;

  public LoginResponse login(LoginRequest request)
      throws UserNotFoundException, VendorNotFoundException {
    final User userDetails = jwtUserDetailsService.loadUserByUsername(request.getPhoneNumber());
    if (userDetails.getUserType() == UserType.ADMIN) {
      AdminUser adminUser = adminUserRepository.findByUserId(userDetails.getId()).orElseThrow(UserType.UserTypeNotFoundException::new);
      if (!adminUser.getApproved()) {
        throw new AdminUserNotApproved();
      }
    }
    final String token ;
    //Login For Customer
    if (request.getVendorId() != null) {
      VendorDTO vendor = vendorService.findById(request.getVendorId());
      User user = userService.findById(vendor.getUserId());
      token = jwtTokenUtil.generateToken(user, user.getTenantId());
    }else{
      token = jwtTokenUtil.generateToken(userDetails);
    }
    Set<Permission> permissions = userDetails.getPermissions();
    Optional<Tenant> tenant = tenantRepository.findByTenantId(userDetails.getTenantId());
    List<VendorPlan> vendorPlans = tenant.stream()
        .flatMap(tenant1 -> tenant1.getVendorPlans().stream())
        .filter(r -> r.getEndDateTime() == null).toList();
    //Check if vendor already has a plan and user permission is not present
    // then use default permission from user_role_feature table
    if (permissions.isEmpty() && !vendorPlans.isEmpty()) {
      List<UserRoleFeature> userRoleFeatures = userRoleFeatureRepository.findByUserType(
          userDetails.getUserType());
      permissions = userRoleFeatures.stream().map(UserRoleFeature::getPermission)
          .collect(Collectors.toSet());
    }
    List<MenuItem> menuItem = MenuItem.createMenu(permissions).stream()
        .sorted(Comparator.comparing(MenuItem::getMenuName)).toList();
    return LoginResponse.builder().userType(userDetails.getUserType())
        .menuItem(menuItem)
        .token(new JwtResponse(token)).user(new UserInfo(userDetails)).build();
  }

  public GenericResponse sendOTP(String phoneNumber) throws OTPNotSentException {
    Boolean aBoolean = userService.existsByPhoneNumber(phoneNumber);
    log.info("Number Exists: " + aBoolean);
    String exists =
            aBoolean ? "Phone number already exists."
                    : "Phone number is new: " + phoneNumber;
    String otp;
    if (aBoolean) {
      otp = otpService.sendOTP(phoneNumber);
    }else{
      throw new OTPNotSentException(exists);
    }
    return new GenericResponse(otp, exists);
  }

  public GenericResponse validateOTP(String phoneNumber, String otp)
          throws InvalidOTPException, UserNotFoundException {
    otpService.validateOTP(phoneNumber, otp);
    User user = userService.findOneByPhoneNumber(phoneNumber);
    userService.validateUser(true, user.getId());
    return new GenericResponse("OTP Validated");
  }

}
