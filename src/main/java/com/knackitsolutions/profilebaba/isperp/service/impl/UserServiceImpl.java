package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.controller.VendorController.SignUpRequest;
import com.knackitsolutions.profilebaba.isperp.entity.main.Permission;
import com.knackitsolutions.profilebaba.isperp.entity.main.Tenant;
import com.knackitsolutions.profilebaba.isperp.entity.main.User;
import com.knackitsolutions.profilebaba.isperp.enums.UserType;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import com.knackitsolutions.profilebaba.isperp.repository.main.UserRepository;
import com.knackitsolutions.profilebaba.isperp.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

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
  public User findByPhoneNumber(String phoneNumber) throws UserNotFoundException {
    return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(
        () -> new UserNotFoundException("User not found with phone number: " + phoneNumber));
  }

  @Override
  public Boolean existsByPhoneNumber(String phoneNumber) {
    return userRepository.existsByPhoneNumber(phoneNumber);
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
  public User findById(Long userId) throws UserNotFoundException {
    return get(userId);
  }

  public User get(Long id) throws UserNotFoundException {
    return userRepository.findById(id).orElseThrow(() -> UserNotFoundException.withId(id));
  }
}
