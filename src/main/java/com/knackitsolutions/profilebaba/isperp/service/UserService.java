package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.controller.AuthenticationController.ChangePassword;
import com.knackitsolutions.profilebaba.isperp.controller.VendorController.SignUpRequest;
import com.knackitsolutions.profilebaba.isperp.dto.CustomerDTO;
import com.knackitsolutions.profilebaba.isperp.entity.main.Permission;
import com.knackitsolutions.profilebaba.isperp.entity.main.Tenant;
import com.knackitsolutions.profilebaba.isperp.entity.main.User;
import com.knackitsolutions.profilebaba.isperp.exception.InvalidOTPException;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import java.util.List;
import org.springframework.security.core.Authentication;

public interface UserService {

  User save(User user);

  void update(String phoneNumber, String password, String rememberMe) throws UserNotFoundException;

  void update(List<Permission> permissions);

  void update(String isPhoneNumberVerified);

  void deleteBySecondaryId(Long userSecondaryId);

  void deleteByUserId(Long userId);

  void deleteByTenantId(Long tenantId);

  void deleteByPhoneNumber(String phoneNumber);

  void validateUser(Boolean isValid, Long userId);

  List<User> findByPhoneNumber(String phoneNumber) throws UserNotFoundException;

  User findOneByPhoneNumber(String phoneNumber) throws UserNotFoundException;

  Boolean existsByPhoneNumber(String phoneNumber);

  Boolean existsByPhoneNumberAndTenantId(String phoneNumber, String tenantId);
  User findByPhoneNumberAndTenantId(String phoneNumber, String tenantId)
      throws UserNotFoundException;

  User save(SignUpRequest signUpRequest, Tenant tenant);

  User save(CustomerDTO customer);

  User findById(Long userId) throws UserNotFoundException;

  void resetPassword(String phoneNumber, String otp, String password)
      throws InvalidOTPException, UserNotFoundException;

  void changePassword(Authentication authentication, ChangePassword request)
      throws UserNotFoundException;

}
