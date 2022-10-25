package com.knackitsolutions.profilebaba.isperp.entity.tenant;

import com.knackitsolutions.profilebaba.isperp.dto.AdjustedBalanceDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.BalanceSheet.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
@Table(name = "adjusted_balance")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdjustedBalance implements Transaction{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDate dated;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

  private String remark;
  private BigDecimal amount;
  private BigDecimal previousBalance;
  private BigDecimal grandTotal;

  @Override
  public BigDecimal getTotalAmount() {
    return amount;
  }

  @Override
  public BigDecimal getFinalAmount(BigDecimal lastAmount) {
    return grandTotal;
  }

  @Override
  public TransactionType getTransactionType() {
    return TransactionType.ADJUSTED_BALANCE;
  }

  @Override
  public Long getTransactionId() {
    return id;
  }

  @Override
  public LocalDateTime getTransactionDate() {
    return getDated().atStartOfDay();
  }

  @Override
  public Long getCustomerId() {
    return getCustomer().getId();
  }

  public AdjustedBalance(AdjustedBalanceDTO dto) {
    setAmount(dto.getAdjustedBalance());
    setRemark(dto.getDetails());
    setDated(LocalDate.now());
  }
}
