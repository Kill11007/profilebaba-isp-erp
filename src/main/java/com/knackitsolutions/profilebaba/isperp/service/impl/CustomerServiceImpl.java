package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.dto.CustomerDTO;
import com.knackitsolutions.profilebaba.isperp.dto.CustomerSummary;
import com.knackitsolutions.profilebaba.isperp.dto.HardwareDetailDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.BillingDetail;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Customer;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.HardwareDetail;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerAlreadyExistsException;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.HardwareNotFoundException;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.BillingDetailsRepository;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.CustomerRepository;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.HardwareDetailRepository;
import com.knackitsolutions.profilebaba.isperp.service.CustomerService;
import com.knackitsolutions.profilebaba.isperp.service.ExcelService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;
  private final HardwareDetailRepository hardwareDetailRepository;
  private final BillingDetailsRepository billingDetailsRepository;
  private final ExcelService excelService;

  private static final Function<Long, CustomerNotFoundException> CUSTOMER_NOT_FOUND_EXCEPTION_FUNCTION = aLong -> new CustomerNotFoundException(
      "Customer not found with id: " + aLong);

  public Customer getCustomerById(Long id) throws CustomerNotFoundException {
    return customerRepository.findById(id)
        .orElseThrow(() -> CUSTOMER_NOT_FOUND_EXCEPTION_FUNCTION.apply(id));
  }

  @Override
  public Customer addCustomer(CustomerDTO dto) throws CustomerAlreadyExistsException {
    Customer customer = new Customer(dto);
    BillingDetail billingDetail = customer.getBillingDetail(); //new BillingDetail(dto.getBillingDetail());
    billingDetail.setCustomer(customer);
    customer.getHardwareDetail()
        .forEach(hardwareDetail -> hardwareDetail.setCustomer(customer));
    Customer save = customerRepository.save(customer);
    billingDetailsRepository.save(billingDetail);
//        .forEach(hardwareDetailRepository::save);
    return save;
  }

  @Override
  public void importCustomers(MultipartFile file) throws CustomerAlreadyExistsException {
    List<Customer> customers = excelService.getCustomers(file);
    List<Customer> newCustomers = customers.stream()
        .peek(customer -> log.info("Customer: " + customer))
        .filter(customer -> !customerRepository.exists(
            (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get("primaryMobileNo"), customer.getPrimaryMobileNo())))
        .peek(customer -> customer.getBillingDetail().setCustomer(customer)).toList();
    log.info("Customers already exists: " + customers);
    customerRepository.saveAll(newCustomers);
    if (newCustomers.size() < customers.size()) {
      customers.removeAll(newCustomers);
      throw new CustomerAlreadyExistsException();
    }
  }

  @Override
  public Resource exportCustomers(MultiValueMap<String, String> filters) {
    //TODO get list
    List<Customer> customers = List.of();
    return excelService.download(customers);
  }

  @Override
  public CustomerDTO getCustomer(Long id) throws CustomerNotFoundException {
    Customer customer = getCustomerById(id);
    CustomerDTO dto = new CustomerDTO(customer);
    return dto;
  }

  @Override
  public void updateCustomer(Long id, CustomerDTO customer) throws CustomerNotFoundException {
    Customer entity = getCustomerById(id);
    entity.update(customer);
    customerRepository.save(entity);
  }

  public void updateCustomer(Customer customer) {
    customerRepository.save(customer);
  }

  @Override
  public void activate(Long id, Boolean active) throws CustomerNotFoundException {
    Customer customerById = getCustomerById(id);
    customerById.setActive(active);
    customerRepository.save(customerById);
  }

  @Override
  public HardwareDetail addHardwareDetail(Long customerId, HardwareDetailDTO hardwareDetail)
      throws CustomerNotFoundException {
    Customer customer = getCustomerById(customerId);
    HardwareDetail hardwareDetailEntity = new HardwareDetail(hardwareDetail);
    hardwareDetailEntity.setCustomer(customer);
    return hardwareDetailRepository.save(hardwareDetailEntity);
  }

  @Override
  public void updateHardwareDetail(Long hardwareDetailId, HardwareDetailDTO hardwareDetail)
      throws HardwareNotFoundException {
    HardwareDetail entity = hardwareDetailRepository.findById(hardwareDetailId)
        .orElseThrow(HardwareNotFoundException::new);
    entity.update(hardwareDetail);
    hardwareDetailRepository.save(entity);
  }

  @Override
  public void removeHardwareDetail(Long hardwareDetailId) throws HardwareNotFoundException {
    HardwareDetail entity = hardwareDetailRepository.findById(hardwareDetailId)
        .orElseThrow(HardwareNotFoundException::new);
    hardwareDetailRepository.delete(entity);
  }

  @Override
  public Page<CustomerSummary> getFilteredCustomer(MultiValueMap<String, String> filters) {
    //TODO add filter and sort and search
    if (filters.isEmpty()) {
      Page<Customer> all = customerRepository.findAll(Pageable.ofSize(10));
      return all.map(CustomerSummary::new);
    }
    return null;
  }

  @Override
  public void deleteCustomer(Long customerID) throws CustomerNotFoundException {
    Customer customerById = getCustomerById(customerID);
    customerRepository.delete(customerById);
  }

  @Override
  public List<Customer> all() {
    List<Customer> customers = new ArrayList<>();
    customerRepository.findAll().forEach(customers::add);
    return customers;
  }

}
