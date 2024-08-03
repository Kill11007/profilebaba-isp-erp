package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.config.TenantContext;
import com.knackitsolutions.profilebaba.isperp.controller.AuthenticationController.ChangePassword;
import com.knackitsolutions.profilebaba.isperp.controller.VendorController.SignUpRequest;
import com.knackitsolutions.profilebaba.isperp.dto.CustomerDTO;
import com.knackitsolutions.profilebaba.isperp.entity.main.Permission;
import com.knackitsolutions.profilebaba.isperp.entity.main.Tenant;
import com.knackitsolutions.profilebaba.isperp.entity.main.User;
import com.knackitsolutions.profilebaba.isperp.enums.UserType;
import com.knackitsolutions.profilebaba.isperp.exception.InvalidOTPException;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import com.knackitsolutions.profilebaba.isperp.repository.main.UserRepository;
import com.knackitsolutions.profilebaba.isperp.service.OTPService;
import com.knackitsolutions.profilebaba.isperp.service.PermissionService;
import com.knackitsolutions.profilebaba.isperp.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final OTPService otpService;
  private final PermissionService permissionService;


  @Override
  public User save(User user) {
    return userRepository.save(user);
  }

  @Override
  public void update(String phoneNumber, String password, String rememberMe)
      throws UserNotFoundException {

  }

  @Override
  public void update(List<Permission> permissions) {

  }

  @Override
  public void update(String isPhoneNumberVerified) {

  }

  @Override
  public void deleteBySecondaryId(Long userSecondaryId) {

  }

  @Override
  public void deleteByUserId(Long userId) {

  }

  @Override
  public void deleteByTenantId(Long tenantId) {

  }

  @Override
  public void deleteByPhoneNumber(String phoneNumber) {

  }

  @Override
  public void validateUser(Boolean isValid, Long userId) {
    userRepository.validateUser(isValid, userId);
  }

  @Override
  public List<User> findByPhoneNumber(String phoneNumber) throws UserNotFoundException {
    List<Optional<User>> users = userRepository.findByPhoneNumber(phoneNumber);
    if (users.size() == 0) {
      throw new UserNotFoundException("User not found with phone number: " + phoneNumber);
    }
    return users.stream().filter(Optional::isPresent).map(Optional::get)
        .collect(Collectors.toList());
  }

  @Override
  public User findOneByPhoneNumber(String phoneNumber) throws UserNotFoundException {
    return userRepository.findByPhoneNumber(phoneNumber).get(0).orElseThrow(
        () -> new UserNotFoundException("User not found with phone number: " + phoneNumber));
  }

  @Override
  public Boolean existsByPhoneNumber(String phoneNumber) {
    return userRepository.existsByPhoneNumber(phoneNumber);
  }

  @Override
  public Boolean existsByPhoneNumberAndTenantId(String phoneNumber, String tenantId) {
    return userRepository.findByPhoneNumberAndTenantId(phoneNumber, tenantId).isPresent();
  }

  @Override
  public User save(SignUpRequest signUpRequest, Tenant tenant) {
    User user = new User();
    user.setPhoneNumber(signUpRequest.getPhoneNumber());
    user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
    user.setTenantId(tenant.getTenantId());
    user.setUserType(UserType.ISP);
    return userRepository.save(user);
  }

  @Override
  public User save(CustomerDTO customer) {
    User user = new User();
    user.setPhoneNumber(customer.getPrimaryMobileNo());
    user.setUserType(UserType.CUSTOMER);
    String tenantId = TenantContext.getTenantId();
    user.setTenantId(tenantId);
    user.setPassword(passwordEncoder.encode("12345"));  //TODO hard coded the customer password
    return userRepository.save(user);
  }

  @Override
  public User findById(Long userId) throws UserNotFoundException {
    return get(userId);
  }

  public User get(Long id) throws UserNotFoundException {
    return userRepository.findById(id).orElseThrow(() -> UserNotFoundException.withId(id));
  }

  @Override
  public User findByPhoneNumberAndTenantId(String phoneNumber, String tenantId)
      throws UserNotFoundException {
    return userRepository.findByPhoneNumberAndTenantId(phoneNumber, tenantId)
        .orElseThrow(UserNotFoundException::new);
  }

  public void resetPassword(String phoneNumber, String otp, String password)
      throws InvalidOTPException, UserNotFoundException {
    User user = this.findOneByPhoneNumber(phoneNumber);
    otpService.validateOTP(phoneNumber, otp);
    updatePassword(user, password);
  }

  public void changePassword(Authentication authentication, ChangePassword request)
      throws UserNotFoundException {
    User principal = (User) authentication.getPrincipal();
    User user = this.findById(principal.getId());
    //boolean matches = passwordEncoder.matches(request.getOldPassword(), user.getPassword());
    //if (matches) {
    updatePassword(user, request.getNewPassword());
    //}
  }

  private void updatePassword(User user, String password) {
    user.setPassword(passwordEncoder.encode(password));
    this.save(user);
  }

  public void setUserPermissions(Long userId, List<Long> permissions) {
    User user = findById(userId);
    Set<Permission> permissionSet = permissions.stream().map(permissionService::get).collect(Collectors.toSet());
    setUserPermissions(user, permissionSet);
  }

  public void setUserPermissions(User user, Set<Permission> permissions) {
    user.setPermissions(null);
    userRepository.save(user);
    user.setPermissions(permissions);
    userRepository.save(user);
  }


}
