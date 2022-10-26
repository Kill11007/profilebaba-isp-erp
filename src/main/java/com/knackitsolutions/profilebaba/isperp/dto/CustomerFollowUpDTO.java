package com.knackitsolutions.profilebaba.isperp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.CustomerFollowUp;
import com.knackitsolutions.profilebaba.isperp.enums.DateTimeFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerFollowUpDTO {
  private Long id;
  private String reason;
  @JsonFormat(pattern = DateTimeFormat.DATE, shape = Shape.STRING)
  private LocalDate followUpDate;
  private LocalDateTime updateDate;
  private Long customerId;

  public CustomerFollowUpDTO(CustomerFollowUp entity) {
    this.setId(entity.getId());
    this.setCustomerId(entity.getCustomer().getId());
    this.setFollowUpDate(entity.getFollowUpDate());
    this.setUpdateDate(entity.getUpdatedDate());
    this.setReason(entity.getReason());
  }
}
