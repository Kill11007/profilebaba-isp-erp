package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.dto.CustomerDTO;
import com.knackitsolutions.profilebaba.isperp.dto.CustomerQuery;
import com.knackitsolutions.profilebaba.isperp.dto.EmployeeDTO;
import com.knackitsolutions.profilebaba.isperp.dto.EmployeeQuery;
import com.knackitsolutions.profilebaba.isperp.dto.IspDTO;
import com.knackitsolutions.profilebaba.isperp.dto.IspQuery;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.VendorNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IspService {

  Page<IspDTO> getISPs() throws UserNotFoundException;

  Page<IspDTO> getISPs(IspQuery ispQuery) throws UserNotFoundException;

  Page<EmployeeDTO> getEmployees(Long vendorId, EmployeeQuery employeeQuery)
      throws VendorNotFoundException, UserNotFoundException;

  Page<CustomerDTO> getCustomers(Long vendorId, CustomerQuery customerQuery)
      throws VendorNotFoundException, UserNotFoundException;

}
