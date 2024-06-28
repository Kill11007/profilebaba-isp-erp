package com.knackitsolutions.profilebaba.isperp.entity.tenant;

import com.knackitsolutions.profilebaba.isperp.dto.ComplaintDTO;
import com.knackitsolutions.profilebaba.isperp.enums.ComplaintStatus;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "complaints")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Complaint {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String complaintNumber;

  private String message;

  private ComplaintStatus status;

  private LocalDateTime startDate;

  private LocalDateTime updatedDate;

  @ManyToOne
  private Customer customer;

  @ManyToOne
  private Employee employee;

  private Long createdByUserId;

  private String employeeRemark;

  public Complaint(ComplaintDTO dto) {
    this.setMessage(dto.getMessage());
    this.setStartDate(LocalDateTime.now());
    this.setStatus(ComplaintStatus.OPEN);
    this.setUpdatedDate(LocalDateTime.now());
    this.setEmployeeRemark(dto.getEmployeeRemark());
  }

}
