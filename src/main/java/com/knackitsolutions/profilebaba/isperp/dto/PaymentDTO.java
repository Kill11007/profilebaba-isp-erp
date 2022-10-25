package com.knackitsolutions.profilebaba.isperp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Payment;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Payment.PaymentMode;
import com.knackitsolutions.profilebaba.isperp.enums.DateTimeFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentDTO {
  private Long id;
  private String receiptNumber;
  private Long customerId;
  private Long employeeId;
  private String collectedBy;
  private PaymentMode paymentMode;
  private BigDecimal previousBalance;
  private BigDecimal paidAmount;
  private BigDecimal discount;
  private BigDecimal netAmount;
  private BigDecimal remainingAmount;
  private String comment;
  @JsonFormat(pattern = DateTimeFormat.DATE_TIME, shape = Shape.STRING)
  private LocalDateTime paymentDateTime;

  public PaymentDTO(Payment entity) {
    setId(entity.getId());
    setComment(entity.getComment());
    setCollectedBy(entity.getCollectedBy());
    setDiscount(entity.getDiscount());
    setPaymentMode(entity.getPaymentMode());
    setCustomerId(entity.getCustomerId());
    setEmployeeId(entity.getEmployee().getId());
    setNetAmount(entity.getNetAmount());
    setReceiptNumber(entity.getReceiptNumber());
    setPaidAmount(entity.getPaidAmount());
    setPaymentDateTime(entity.getPaymentDateTime());
    setPreviousBalance(entity.getPreviousBalance());
    setRemainingAmount(entity.getRemainingAmount());
  }
}
