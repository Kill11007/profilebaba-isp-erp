package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.config.TenantContext;
import com.knackitsolutions.profilebaba.isperp.dto.CustomerDTO;
import com.knackitsolutions.profilebaba.isperp.dto.CustomerQuery;
import com.knackitsolutions.profilebaba.isperp.dto.EmployeeDTO;
import com.knackitsolutions.profilebaba.isperp.dto.EmployeeQuery;
import com.knackitsolutions.profilebaba.isperp.dto.IspDTO;
import com.knackitsolutions.profilebaba.isperp.dto.IspQuery;
import com.knackitsolutions.profilebaba.isperp.dto.IspSpecification;
import com.knackitsolutions.profilebaba.isperp.dto.TenantDTO;
import com.knackitsolutions.profilebaba.isperp.dto.UserDTO;
import com.knackitsolutions.profilebaba.isperp.dto.VendorDTO;
import com.knackitsolutions.profilebaba.isperp.dto.VendorSpecification;
import com.knackitsolutions.profilebaba.isperp.entity.main.Tenant;
import com.knackitsolutions.profilebaba.isperp.entity.main.User;
import com.knackitsolutions.profilebaba.isperp.entity.main.Vendor;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Customer;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Employee;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.VendorNotFoundException;
import com.knackitsolutions.profilebaba.isperp.repository.main.TenantRepository;
import com.knackitsolutions.profilebaba.isperp.repository.main.UserRepository;
import com.knackitsolutions.profilebaba.isperp.repository.main.VendorRepository;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.CustomerRepository;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.EmployeeRepository;
import com.knackitsolutions.profilebaba.isperp.service.IspService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IspServiceImpl implements IspService {

  private final VendorRepository vendorRepository;
  private final UserRepository userRepository;
  private final TenantRepository tenantRepository;
  private final EmployeeRepository employeeRepository;
  private final CustomerRepository customerRepository;

  @Override
  public List<IspDTO> getISPs(IspQuery ispQuery) throws UserNotFoundException {
    IspSpecification ispSpecification = ispQuery.toSpecification();
    VendorSpecification vendorSpecification = ispSpecification.getVendorSpecification();
    List<IspDTO> isps = new ArrayList<>();
    if (vendorSpecification != null) {
      Page<Vendor> all = vendorRepository.findAll(vendorSpecification, ispQuery.getPageable());
      for (Vendor vendor : all) {
        User user = userRepository.findById(vendor.getUserId()).orElseThrow(
            UserNotFoundException::new);
        Tenant tenant = tenantRepository.findByTenantId(user.getTenantId())
            .orElseThrow(() -> new RuntimeException("Tenant not found exception"));
        IspDTO ispDTO = new IspDTO(new UserDTO(user), new TenantDTO(tenant), new VendorDTO(vendor));
        isps.add(ispDTO);
      }
    }
    return isps;
  }

  @Override
  public List<EmployeeDTO> getEmployees(Long vendorId, EmployeeQuery employeeQuery)
      throws VendorNotFoundException, UserNotFoundException {
    Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(VendorNotFoundException::new);
    User user = userRepository.findById(vendor.getUserId()).orElseThrow(UserNotFoundException::new);
    TenantContext.setTenantId(user.getTenantId());
    List<Employee> all;
    if (employeeQuery == null) {
      all = employeeRepository.findAll();
    }else{
      all = employeeRepository.findAll(employeeQuery.toSpecification());
    }
    List<EmployeeDTO> employees = new ArrayList<>();
    for (Employee employee : all) {
      User employeeUser = userRepository.findById(employee.getUserId())
          .orElseThrow(UserNotFoundException::new);
      EmployeeDTO employeeDTO = new EmployeeDTO(employee, employeeUser);
      employees.add(employeeDTO);
    }
    return employees;
  }

  @Override
  public List<CustomerDTO> getCustomers(Long vendorId, CustomerQuery customerQuery)
      throws VendorNotFoundException, UserNotFoundException {
    Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(VendorNotFoundException::new);
    User user = userRepository.findById(vendor.getUserId()).orElseThrow(UserNotFoundException::new);
    TenantContext.setTenantId(user.getTenantId());
    List<Customer> all ;
    if (customerQuery != null) {
      all = customerRepository.findAll(customerQuery.toSpecification());
    }else{
      all = customerRepository.findAll();
    }
    return all.stream()
        .map(CustomerDTO::new).collect(Collectors.toList());
  }

}
