package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.dto.ComplaintDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Complaint;
import com.knackitsolutions.profilebaba.isperp.exception.CustomException;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.EmployeeNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ComplaintService {

  Page<Complaint> all(Pageable pageable);

  Page<ComplaintDTO> allDTO(Pageable pageable);

  Complaint one(Long id) throws ComplaintNotFoundException;

  ComplaintDTO oneDTO(Long id)
      throws ComplaintNotFoundException, UserNotFoundException, EmployeeNotFoundException, CustomerNotFoundException;

  Complaint newComplaint(Complaint complaint);

  Complaint newComplaint(ComplaintDTO dto)
      throws CustomerNotFoundException, EmployeeNotFoundException, UserNotFoundException;

  void update(Long id, ComplaintDTO dto) throws EmployeeNotFoundException, UserNotFoundException;

  void updateStatus(Long id, String status);
  void updateEmployeeRemark(Long id, String status);

  void assignAgent(Long id, Long employeeId) throws EmployeeNotFoundException, UserNotFoundException;

  void delete(Long id);

  @Getter
  class ComplaintNotFoundException extends CustomException {
    private final Long complaintId;

    public ComplaintNotFoundException(Long complaintId) {
      super("Complain with id: " + complaintId + " not found");
      this.complaintId = complaintId;
    }
  }
}
