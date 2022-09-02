package com.knackitsolutions.profilebaba.isperp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Subscription;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Subscription.SubscriptionStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDTO {
  private Long id;

  @NotNull
  private Long planId;

  private String planName;

  private BigDecimal fixedBillAmountPerMonth;

  private int quantity;

  private Boolean futureDays;

  private SubscriptionStatus status;

  // No of months
  private int period;

  @JsonFormat(pattern = "dd-MM-yyyy", shape = Shape.STRING)
  private LocalDate startDate;
  @JsonFormat(pattern = "dd-MM-yyyy", shape = Shape.STRING)
  private LocalDate endDate;

  public SubscriptionDTO(Subscription entity) {
    setId(entity.getId());
    setPlanId(entity.getPlan().getId());
    setPlanName(entity.getPlan().getName());
    setFixedBillAmountPerMonth(entity.getFixedBillAmountPerMonth());
    setQuantity(entity.getQuantity());
    setFutureDays(entity.getFutureDays());
    setStatus(entity.getStatus());
    setStartDate(entity.getStartDate());
    setEndDate(entity.getEndDate());
    setPeriod(entity.getPeriod());
  }
}
