package com.knackitsolutions.profilebaba.isperp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.BillingDetail;
import com.knackitsolutions.profilebaba.isperp.enums.BillDuration;
import com.knackitsolutions.profilebaba.isperp.enums.BillType;
import com.knackitsolutions.profilebaba.isperp.enums.GstType;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingDetailDTO {
  private Long customerId;
  @JsonFormat(pattern="yyyy-MM-dd")
  private LocalDate startDate;
  private BigDecimal openingOutstandingBalance;
  private BigDecimal openingAdvanceBalance;
  private BigDecimal monthlyAdditionalCharge;
  private BigDecimal monthlyDiscount;
  private BillDuration billDuration;
  private Integer billDurationValue;
  private BillType billType;
  private GstType gstType;

  public BillingDetailDTO(BillingDetail billingDetail) {
    if (billingDetail == null) {
      return;
    }
    this.setCustomerId(billingDetail.getCustomer().getId());
    this.setBillDuration(billingDetail.getBillDuration());
    this.setBillType(billingDetail.getBillType());
    this.setBillDurationValue(billingDetail.getBillDurationValue());
    this.setGstType(billingDetail.getGstType());
    this.setMonthlyAdditionalCharge(billingDetail.getMonthlyAdditionalCharge());
    this.setMonthlyDiscount(billingDetail.getMonthlyDiscount());
    this.setOpeningAdvanceBalance(billingDetail.getOpeningAdvanceBalance());
    this.setOpeningOutstandingBalance(billingDetail.getOpeningOutstandingBalance());
    this.setStartDate(billingDetail.getStartDate());
  }
}
