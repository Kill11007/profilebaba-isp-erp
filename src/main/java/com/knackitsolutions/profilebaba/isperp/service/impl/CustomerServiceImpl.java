package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.config.TenantContext;
import com.knackitsolutions.profilebaba.isperp.dto.CustomerDTO;
import com.knackitsolutions.profilebaba.isperp.dto.CustomerSummary;
import com.knackitsolutions.profilebaba.isperp.dto.HardwareDetailDTO;
import com.knackitsolutions.profilebaba.isperp.entity.main.User;
import com.knackitsolutions.profilebaba.isperp.entity.main.Vendor;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.BillingDetail;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Customer;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.HardwareDetail;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerAlreadyExistsException;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.HardwareNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.BillingDetailsRepository;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.CustomerRepository;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.CustomerRoleRepository;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.HardwareDetailRepository;
import com.knackitsolutions.profilebaba.isperp.service.CustomerService;
import com.knackitsolutions.profilebaba.isperp.service.ExcelService;
import com.knackitsolutions.profilebaba.isperp.service.UserService;

import java.util.ArrayList;
import java.util.List;
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
    private final UserService userService;
    private final VendorService vendorService;
    private final CustomerRoleRepository customerRoleRepository;

    private static final Function<Long, CustomerNotFoundException> CUSTOMER_NOT_FOUND_EXCEPTION_FUNCTION = aLong -> new CustomerNotFoundException(
            "Customer not found with id: " + aLong);

    public Customer getCustomerById(Long id) throws CustomerNotFoundException {
        return customerRepository.findById(id)
                .orElseThrow(() -> CUSTOMER_NOT_FOUND_EXCEPTION_FUNCTION.apply(id));
    }

    @Override
    public Customer addCustomer(CustomerDTO dto) throws CustomerAlreadyExistsException {
        if (customerRepository.existsByCustomerCode(dto.getCustomerCode())) {
            throw new CustomerAlreadyExistsException("Already exists with customer code");
        }
        if (customerRepository.existsByPrimaryMobileNo(dto.getPrimaryMobileNo())) {
            throw new CustomerAlreadyExistsException("Already exists with mobile number");
        }
        if (!userService.existsByPhoneNumberAndTenantId(dto.getPrimaryMobileNo(),
                TenantContext.getTenantId())) {
            User user = userService.save(dto);
            dto.setUserId(user.getId());
        }
        Customer customer = new Customer(dto);
        BillingDetail billingDetail = customer.getBillingDetail(); //new BillingDetail(dto.getBillingDetail());
        billingDetail.setCustomer(customer);
        customer.getHardwareDetail()
                .forEach(hardwareDetail -> hardwareDetail.setCustomer(customer));
        customerRoleRepository.findById(dto.getCustomerRoleId()).ifPresent(customer::setCustomerRole);  // TODO add permission to user permission table
        Customer save = customerRepository.save(customer);
        billingDetailsRepository.save(billingDetail);
        return save;
    }

    @Override
    public void importCustomers(MultipartFile file) throws CustomerAlreadyExistsException {
        List<CustomerDTO> customers = excelService.getCustomers(file);
        List<CustomerDTO> alreadyExists = new ArrayList<>();
        Function<CustomerDTO, Customer> addCustomer = customerDTO -> {
            Customer customer = null;
            try {
                customer = this.addCustomer(customerDTO);
            } catch (CustomerAlreadyExistsException e) {
                alreadyExists.add(customerDTO);
            }
            return customer;
        };
        List<Customer> newCustomers = customers.stream()
                .peek(customer -> log.info("Customer: " + customer))
                .map(addCustomer).toList();
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

    //One customer can be present at multiple vendor

    @Override
    public List<Vendor> getVendors(String phoneNumber) throws UserNotFoundException {
        List<User> allByPhoneNumber = userService.findByPhoneNumber(phoneNumber);
        List<Vendor> vendors = new ArrayList<>();
        for (User user : allByPhoneNumber) {
            Vendor vendor = vendorService.findByUserId(user.getId());
            vendors.add(vendor);
        }
        return vendors;
    }

    @Override
    public Customer findByUserId(Long userId) throws CustomerNotFoundException {
        return customerRepository.findByUserId(userId).orElseThrow(CustomerNotFoundException::new);
    }


}
