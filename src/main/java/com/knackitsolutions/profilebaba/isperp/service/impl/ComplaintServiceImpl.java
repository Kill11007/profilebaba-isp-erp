package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.dto.ComplaintDTO;
import com.knackitsolutions.profilebaba.isperp.entity.main.User;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Complaint;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Customer;
import com.knackitsolutions.profilebaba.isperp.enums.ComplaintStatus;
import com.knackitsolutions.profilebaba.isperp.enums.UserType;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.EmployeeNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import com.knackitsolutions.profilebaba.isperp.helper.UserServiceHelper;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.ComplaintRepository;
import com.knackitsolutions.profilebaba.isperp.service.ComplaintService;
import com.knackitsolutions.profilebaba.isperp.service.CustomerService;
import com.knackitsolutions.profilebaba.isperp.service.EmployeeService;
import com.knackitsolutions.profilebaba.isperp.service.IAuthenticationFacade;
import java.time.LocalDateTime;
import java.util.Locale;
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
  private final UserServiceHelper userServiceHelper;
  private final IAuthenticationFacade authenticationFacade;

  @Override
  public Page<Complaint> all(Pageable pageable) {
    return repository.findAll(pageable);
  }

  @Override
  public Page<ComplaintDTO> allDTO(Pageable pageable) {
    return all(pageable).map(ComplaintDTO::new).map(dto -> {
      dto.setCreatedByUserName(userServiceHelper.getUserName(dto.getCreatedByUserId()));
      return dto;
    });
  }

  @Override
  public Complaint one(Long id) throws ComplaintNotFoundException {
    return repository.findById(id)
        .orElseThrow(() -> new ComplaintNotFoundException(id));
  }

  @Override
  public ComplaintDTO oneDTO(Long id)
      throws ComplaintNotFoundException{
    ComplaintDTO complaintDTO = new ComplaintDTO(one(id));
    complaintDTO.setCreatedByUserName(userServiceHelper.getUserName(
        complaintDTO.getCreatedByUserId()));
    return complaintDTO;
  }

  @Override
  public Complaint newComplaint(Complaint complaint) {
    return repository.save(complaint);
  }

  @Override
  public Complaint newComplaint(ComplaintDTO dto)
      throws CustomerNotFoundException, EmployeeNotFoundException, UserNotFoundException {
    Complaint complaint = new Complaint(dto);
    User user = (User)authenticationFacade.getAuthentication().getPrincipal();
    complaint.setCreatedByUserId(user.getId());
    complaint.setComplaintNumber(createComplaintNumber(user.getTenantId()));
    // Find the customer-id from user-id
    if (dto.getCustomerId() == null && user.getUserType() == UserType.CUSTOMER) {
      Customer customer = customerService.findByUserId(user.getId());
      complaint.setCustomer(customer);
    }else{
      if(dto.getCustomerId() == null){
        throw new CustomerNotFoundException();
      }
      complaint.setCustomer(customerService.getCustomerById(dto.getCustomerId()));
    }
    if (dto.getEmployeeId() != null) {
      complaint.setEmployee(employeeService.findById(dto.getEmployeeId()));
    }
    return newComplaint(complaint);
  }

  private String createComplaintNumber(String tenantId) {
    Long maxId = repository.findMaxId();
    return firstThreeLetter(tenantId) + "00" + (maxId == null ? 1 : maxId + 1);
  }

  private String firstThreeLetter(String tenantId) {
    String[] split = tenantId.split("T-");
    if (split.length < 2) {
      return "C";
    }
    String name = split[1];
    if (name.length() > 3) {
      return name.substring(0, 3).toUpperCase(Locale.ROOT);
    }
    return name.toLowerCase(Locale.ROOT);
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
  public void updateEmployeeRemark(Long id, String employeeRemark) {
    Complaint one = one(id);
    String remark = one.getEmployeeRemark() == null ? "" : one.getEmployeeRemark();
    String updatedRemark = remark.concat("\n").concat(employeeRemark);
    one.setEmployeeRemark(updatedRemark);
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
