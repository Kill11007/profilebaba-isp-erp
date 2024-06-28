package com.knackitsolutions.profilebaba.isperp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Complaint;
import com.knackitsolutions.profilebaba.isperp.enums.ComplaintStatus;
import com.knackitsolutions.profilebaba.isperp.enums.DateTimeFormat;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComplaintDTO {
  private Long id;
  private String message;
  @JsonFormat(pattern = DateTimeFormat.DATE_TIME, shape = Shape.STRING)
  private LocalDateTime startDate;
  private LocalDateTime updatedDate;
  private ComplaintStatus status;
  private CustomerDTO customer;
  private EmployeeDTO employee;
  private Long customerId;
  private Long employeeId;
  private String employeeRemark;
  private Long createdByUserId;
  private String createdByUserName;
  private String complaintNumber;

  public ComplaintDTO(Complaint entity) {
    this.setMessage(entity.getMessage());
    this.setStartDate(entity.getStartDate());
    this.setUpdatedDate(entity.getUpdatedDate());
    this.setCustomerId(entity.getCustomer().getId());
    this.setEmployeeId(entity.getEmployee() == null ? null : entity.getEmployee().getId());
    this.setCustomer(new CustomerDTO(entity.getCustomer()));
    this.setEmployee(entity.getEmployee() == null ? null : new EmployeeDTO(entity.getEmployee()));
    this.setId(entity.getId());
    this.setStatus(entity.getStatus());
    this.setEmployeeRemark(entity.getEmployeeRemark());
    this.setCreatedByUserId(entity.getCreatedByUserId());
    this.setComplaintNumber(entity.getComplaintNumber());
  }
}
