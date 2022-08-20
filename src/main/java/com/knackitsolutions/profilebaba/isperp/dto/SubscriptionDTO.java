package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.Subscription;
import com.knackitsolutions.profilebaba.isperp.entity.Subscription.SubscriptionStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
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

  private Long planId;

  private String planName;

  private BigDecimal fixedBillAmountPerMonth;

  private int quantity;

  private Boolean futureDays;

  private SubscriptionStatus status;

  // No of months
  private int period;

  private LocalDate startDate;
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
