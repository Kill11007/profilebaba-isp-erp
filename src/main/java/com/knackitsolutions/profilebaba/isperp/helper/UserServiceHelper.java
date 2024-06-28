package com.knackitsolutions.profilebaba.isperp.helper;

import com.knackitsolutions.profilebaba.isperp.entity.main.User;
import com.knackitsolutions.profilebaba.isperp.enums.UserType;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.EmployeeNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.CustomerService;
import com.knackitsolutions.profilebaba.isperp.service.EmployeeService;
import com.knackitsolutions.profilebaba.isperp.service.UserService;
import com.knackitsolutions.profilebaba.isperp.service.impl.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserServiceHelper {

  private final UserService userService;
  private final CustomerService customerService;
  private final EmployeeService employeeService;
  private final VendorService vendorService;
  public String getUserName(Long userId) {
    User user = null;
    try {
      user = userService.findById(userId);
    } catch (UserNotFoundException e) {
      throw new RuntimeException(e);
    }
    if (user.getUserType() == UserType.ISP) {
      try {
        return vendorService.findByUserId(userId).getBusinessName();
      } catch (UserNotFoundException e) {
        throw new RuntimeException(e);
      }
    } else if (user.getUserType() == UserType.EMPLOYEE) {
      try {
        return employeeService.findByUserId(userId).getName();
      } catch (EmployeeNotFoundException e) {
        throw new RuntimeException(e);
      }
    } else if (user.getUserType() == UserType.CUSTOMER) {
      try {
        return customerService.findByUserId(userId).getName();
      } catch (CustomerNotFoundException e) {
        throw new RuntimeException(e);
      }
    }
    return user.getUsername();
  }
}
