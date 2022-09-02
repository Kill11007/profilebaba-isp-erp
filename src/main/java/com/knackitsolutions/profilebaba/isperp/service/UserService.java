package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.controller.VendorController.SignUpRequest;
import com.knackitsolutions.profilebaba.isperp.dto.NewEmployeeRequest;
import com.knackitsolutions.profilebaba.isperp.entity.main.Permission;
import com.knackitsolutions.profilebaba.isperp.entity.main.Tenant;
import com.knackitsolutions.profilebaba.isperp.entity.main.User;
import com.knackitsolutions.profilebaba.isperp.entity.main.Vendor;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Employee;
import com.knackitsolutions.profilebaba.isperp.exception.UserAlreadyExistsException;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import java.util.List;

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

  User findByPhoneNumber(String phoneNumber) throws UserNotFoundException;

  Boolean existsByPhoneNumber(String phoneNumber);

  User save(SignUpRequest signUpRequest, Tenant tenant);

  User findById(Long userId) throws UserNotFoundException;

}
