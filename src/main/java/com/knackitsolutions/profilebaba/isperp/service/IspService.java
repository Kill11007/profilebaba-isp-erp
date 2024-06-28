package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.dto.CustomerDTO;
import com.knackitsolutions.profilebaba.isperp.dto.CustomerQuery;
import com.knackitsolutions.profilebaba.isperp.dto.EmployeeDTO;
import com.knackitsolutions.profilebaba.isperp.dto.EmployeeQuery;
import com.knackitsolutions.profilebaba.isperp.dto.IspDTO;
import com.knackitsolutions.profilebaba.isperp.dto.IspQuery;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.VendorNotFoundException;
import java.util.List;

public interface IspService {

  List<IspDTO> getISPs(IspQuery ispQuery) throws UserNotFoundException;

  List<EmployeeDTO> getEmployees(Long vendorId, EmployeeQuery employeeQuery)
      throws VendorNotFoundException, UserNotFoundException;

  List<CustomerDTO> getCustomers(Long vendorId, CustomerQuery customerQuery)
      throws VendorNotFoundException, UserNotFoundException;

}
