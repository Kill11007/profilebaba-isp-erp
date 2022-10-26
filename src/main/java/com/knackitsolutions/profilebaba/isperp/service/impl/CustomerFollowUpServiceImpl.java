package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.dto.CustomerFollowUpDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Customer;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.CustomerFollowUp;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerNotFoundException;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.CustomerFollowUpRepository;
import com.knackitsolutions.profilebaba.isperp.service.CustomerFollowUpService;
import com.knackitsolutions.profilebaba.isperp.service.CustomerService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerFollowUpServiceImpl implements CustomerFollowUpService {

  private final CustomerFollowUpRepository repository;
  private final CustomerService customerService;

  @Override
  public List<CustomerFollowUp> all() {
    return repository.findAll();
  }

  @Override
  public Page<CustomerFollowUp> all(Pageable pageable) {
    return repository.findAll(pageable);
  }

  @Override
  public List<CustomerFollowUpDTO> allDTOs() {
    return all().stream().map(CustomerFollowUpDTO::new).collect(Collectors.toList());
  }

  @Override
  public Page<CustomerFollowUpDTO> allDTOs(Pageable pageable) {
    return all(pageable).map(CustomerFollowUpDTO::new);
  }

  @Override
  public CustomerFollowUp one(Long customerId) {
    return repository.findByCustomer_Id(customerId).orElseThrow(
        CustomerFollowUpNotFoundException::new);
  }

  @Override
  public CustomerFollowUp oneById(Long id) {
    return repository.findById(id).orElseThrow(
        CustomerFollowUpNotFoundException::new);
  }

  @Override
  public CustomerFollowUpDTO oneDTO(Long customerId) {
    return new CustomerFollowUpDTO(one(customerId));
  }

  @Override
  public CustomerFollowUpDTO oneDTOById(Long id) {
    return new CustomerFollowUpDTO(oneById(id));
  }

  @Override
  public CustomerFollowUp newFollowUp(CustomerFollowUpDTO dto) throws CustomerNotFoundException {
    CustomerFollowUp customerFollowUp = new CustomerFollowUp(dto);
    Customer customerById = customerService.getCustomerById(dto.getCustomerId());
    customerFollowUp.setCustomer(customerById);
    return repository.save(customerFollowUp);
  }

  @Override
  public CustomerFollowUp updateFollowUp(CustomerFollowUpDTO dto) {
    CustomerFollowUp customerFollowUp = repository.findById(dto.getId()).orElseThrow(
        CustomerFollowUpNotFoundException::new);
    customerFollowUp.update(dto);
    return repository.save(customerFollowUp);
  }

  @Override
  public void deleteFollowUp(Long id) {
    repository.deleteById(id);
  }
}
