package com.knackitsolutions.profilebaba.isperp.entity.tenant;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
  private TransactionType transactionType;
  private Long transactionId;

  private BigDecimal txnAmount;
  @Column(name = "final")
  private BigDecimal finalAmount;

  public enum TransactionType{
    BILL("BILL"),
    PAYMENT("PAYMENT"),
    ADJUSTED_BALANCE("ADJUSTED_BALANCE");
    @Getter
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

}
