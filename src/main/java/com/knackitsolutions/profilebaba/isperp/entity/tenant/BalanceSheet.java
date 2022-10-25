package com.knackitsolutions.profilebaba.isperp.entity.tenant;

import com.fasterxml.jackson.annotation.JsonValue;
import java.math.BigDecimal;
import java.time.LocalDate;
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
import lombok.ToString.Exclude;

@Entity
@Table(name = "balance_sheet")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceSheet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDate dated;
  @Column(columnDefinition = "ENUM('BILL', 'PAYMENT', 'ADJUSTED_BALANCE')")
  private TransactionType transactionType;
  private Long transactionId;

  private BigDecimal txnAmount;
  @Column(name = "final")
  private BigDecimal finalAmount;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  @Exclude
  private Customer customer;

  public enum TransactionType{
    BILL("BILL"),
    PAYMENT("PAYMENT"),
    ADJUSTED_BALANCE("ADJUSTED_BALANCE");
    @Getter
    @JsonValue
    private final String type;
    TransactionType(String type) {
      this.type = type;
    }

    public static TransactionType of(String type) {
      return Arrays.stream(TransactionType.values()).filter(t -> t.getType().equalsIgnoreCase(type))
          .findFirst().orElseThrow(TransactionTypeNotFoundException::new);
    }
  }

  public static class TransactionTypeNotFoundException extends RuntimeException {

  }

  public BalanceSheet(Transaction transaction, BigDecimal lastAmount) {
    setTransactionId(transaction.getTransactionId());
    setTransactionType(transaction.getTransactionType());
    setTxnAmount(transaction.getTotalAmount());
    setFinalAmount(transaction.getFinalAmount(lastAmount));
    setDated(transaction.getTransactionDate().toLocalDate());
    setCustomer(transaction.getCustomer());
  }

}
