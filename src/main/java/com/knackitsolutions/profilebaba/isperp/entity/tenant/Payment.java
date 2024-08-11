package com.knackitsolutions.profilebaba.isperp.entity.tenant;

import com.fasterxml.jackson.annotation.JsonValue;
import com.knackitsolutions.profilebaba.isperp.dto.PaymentDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.BalanceSheet.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "payments")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment implements Transaction{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "receipt_no")
  private String receiptNumber;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @ManyToOne
  @JoinColumn(name = "collection_agent_id")
  private Employee employee;

  private String collectedBy;
  private PaymentMode paymentMode;
  private BigDecimal previousBalance;
  private BigDecimal paidAmount;
  private BigDecimal discount;
  private BigDecimal netAmount;
  private BigDecimal remainingAmount;
  private String comment;
  private LocalDateTime paymentDateTime;

  @Getter
  public enum PaymentMode{
    ONLINE("ONLINE"),
    CASH("CASH");

    @JsonValue
    private final String paymentMode;

    PaymentMode(String paymentMode) {
      this.paymentMode = paymentMode;
    }

    public static PaymentMode of(String mode) {
      return Arrays.stream(PaymentMode.values())
          .filter(pm -> pm.getPaymentMode().equalsIgnoreCase(mode)).findFirst()
          .orElseThrow(PaymentModeNotFoundException::new);
    }
  }

  public Payment(PaymentDTO dto) {
    setComment(dto.getComment());
    setDiscount(dto.getDiscount());
    setPaidAmount(dto.getPaidAmount());
    setPaymentMode(dto.getPaymentMode());
    this.paymentDateTime = dto.getPaymentDateTime() == null ? LocalDateTime.now() : getPaymentDateTime();
  }

  public static class PaymentModeNotFoundException extends RuntimeException{

  }

  @Override
  public BigDecimal getTotalAmount() {
    return netAmount;
  }

  @Override
  public BigDecimal getFinalAmount(BigDecimal lastAmount) {
    return lastAmount.subtract(netAmount);
  }

  @Override
  public TransactionType getTransactionType() {
    return TransactionType.PAYMENT;
  }

  @Override
  public Long getTransactionId() {
    return id;
  }

  @Override
  public LocalDateTime getTransactionDate() {
    return getPaymentDateTime();
  }

  @Override
  public Long getCustomerId() {
    return getCustomer().getId();
  }
}
