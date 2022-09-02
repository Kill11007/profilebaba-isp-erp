package com.knackitsolutions.profilebaba.isperp.entity.tenant;

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
public class Payment {

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

  public enum PaymentMode{
    ONLINE("ONLINE"),
    CASH("CASH");

    @Getter
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

  public static class PaymentModeNotFoundException extends RuntimeException{

  }

}
