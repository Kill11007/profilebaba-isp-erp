package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.dto.ComplaintDTO;
import com.knackitsolutions.profilebaba.isperp.dto.EmployeeDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Complaint;
import com.knackitsolutions.profilebaba.isperp.enums.ComplaintStatus;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.EmployeeNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.ComplaintRepository;
import com.knackitsolutions.profilebaba.isperp.service.ComplaintService;
import com.knackitsolutions.profilebaba.isperp.service.CustomerService;
import com.knackitsolutions.profilebaba.isperp.service.EmployeeService;
import com.knackitsolutions.profilebaba.isperp.service.UserService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {

  private final ComplaintRepository repository;
  private final EmployeeService employeeService;
  private final CustomerService customerService;
  private final UserService userService;

  @Override
  public Page<Complaint> all(Pageable pageable) {
    return repository.findAll(pageable);
  }

  @Override
  public Page<ComplaintDTO> allDTO(Pageable pageable) {
    return all(pageable).map(ComplaintDTO::new);
  }

  @Override
  public Complaint one(Long id) throws ComplaintNotFoundException {
    return repository.findById(id)
        .orElseThrow(() -> new ComplaintNotFoundException(id));
  }

  @Override
  public ComplaintDTO oneDTO(Long id)
      throws ComplaintNotFoundException {
    return new ComplaintDTO(one(id));
  }

  @Override
  public Complaint newComplaint(Complaint complaint) {
    return repository.save(complaint);
  }

  @Override
  public Complaint newComplaint(ComplaintDTO dto)
      throws CustomerNotFoundException, EmployeeNotFoundException, UserNotFoundException {
    Complaint complaint = new Complaint(dto);
    complaint.setCustomer(customerService.getCustomerById(dto.getCustomerId()));
    complaint.setEmployee(employeeService.findById(dto.getEmployeeId()));
    return newComplaint(complaint);
  }

  @Override
  public void update(Long id, ComplaintDTO dto)
      throws EmployeeNotFoundException, UserNotFoundException {
    Complaint complaint = one(id);
    complaint.setStatus(dto.getStatus());
    complaint.setMessage(dto.getMessage());
    complaint.setUpdatedDate(LocalDateTime.now());
    repository.save(complaint);
  }

  @Override
  public void updateStatus(Long id, String status) {
    Complaint one = one(id);
    one.setStatus(ComplaintStatus.of(status));
    repository.save(one);
  }

  @Override
  public void assignAgent(Long id, Long employeeId)
      throws EmployeeNotFoundException, UserNotFoundException {
    Complaint one = one(id);
    one.setEmployee(employeeService.findById(employeeId));
    repository.save(one);
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }
}
