package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.controller.VendorController.LoginRequest;
import com.knackitsolutions.profilebaba.isperp.dto.JwtResponse;
import com.knackitsolutions.profilebaba.isperp.dto.LoginResponse;
import com.knackitsolutions.profilebaba.isperp.dto.MenuItem;
import com.knackitsolutions.profilebaba.isperp.dto.VendorDTO;
import com.knackitsolutions.profilebaba.isperp.entity.main.Permission;
import com.knackitsolutions.profilebaba.isperp.entity.main.Tenant;
import com.knackitsolutions.profilebaba.isperp.entity.main.User;
import com.knackitsolutions.profilebaba.isperp.entity.main.UserRoleFeature;
import com.knackitsolutions.profilebaba.isperp.entity.main.VendorPlan;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.VendorNotFoundException;
import com.knackitsolutions.profilebaba.isperp.repository.main.TenantRepository;
import com.knackitsolutions.profilebaba.isperp.repository.main.UserRoleFeatureRepository;
import com.knackitsolutions.profilebaba.isperp.service.UserService;
import com.knackitsolutions.profilebaba.isperp.utility.JwtTokenUtil;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final JwtTokenUtil jwtTokenUtil;

  private final JwtUserDetailsService jwtUserDetailsService;
  private final VendorService vendorService;
  private final UserService userService;
  private final UserRoleFeatureRepository userRoleFeatureRepository;
  private final TenantRepository tenantRepository;

  public LoginResponse login(LoginRequest request)
      throws UserNotFoundException, VendorNotFoundException {
    final User userDetails = jwtUserDetailsService.loadUserByUsername(request.getPhoneNumber());;
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
        .filter(r -> r.getEndDateTime() != null).toList();
    //Check if vendor already has a plan and user permission is not present
    // then use default permission from user_role_feature table
    if (!vendorPlans.isEmpty() && permissions.isEmpty()) {
      List<UserRoleFeature> userRoleFeatures = userRoleFeatureRepository.findByUserType(
          userDetails.getUserType());
      permissions = userRoleFeatures.stream().map(UserRoleFeature::getPermission)
          .collect(Collectors.toSet());
    }
    return new LoginResponse(userDetails.getId(), userDetails.getUserType(),
        MenuItem.createMenu(permissions), new JwtResponse(token));
  }
}
