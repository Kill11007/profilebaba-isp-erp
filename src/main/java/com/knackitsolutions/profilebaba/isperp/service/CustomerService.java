package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.dto.CustomerDTO;
import com.knackitsolutions.profilebaba.isperp.dto.CustomerSummary;
import com.knackitsolutions.profilebaba.isperp.dto.HardwareDetailDTO;
import com.knackitsolutions.profilebaba.isperp.entity.Customer;
import com.knackitsolutions.profilebaba.isperp.entity.HardwareDetail;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerAlreadyExistsException;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.HardwareNotFoundException;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

public interface CustomerService {

  Customer addCustomer(CustomerDTO customer) throws CustomerAlreadyExistsException;

  void importCustomers(MultipartFile file) throws CustomerAlreadyExistsException;

  Resource exportCustomers(MultiValueMap<String, String> customers);

  CustomerDTO getCustomer(Long id) throws CustomerNotFoundException;

  void updateCustomer(Long id, CustomerDTO customer)
      throws CustomerNotFoundException;

  void activate(Long id, Boolean active) throws CustomerNotFoundException;

  HardwareDetail addHardwareDetail(Long customerId, HardwareDetailDTO hardwareDetail)
      throws CustomerNotFoundException;

  void updateHardwareDetail(Long hardwareDetailId, HardwareDetailDTO hardwareDetail)
      throws HardwareNotFoundException;

  void removeHardwareDetail(Long hardwareDetailId) throws HardwareNotFoundException;

  Page<CustomerSummary> getFilteredCustomer(MultiValueMap<String, String> filters);

  void deleteCustomer(Long customerID) throws CustomerNotFoundException;
}
