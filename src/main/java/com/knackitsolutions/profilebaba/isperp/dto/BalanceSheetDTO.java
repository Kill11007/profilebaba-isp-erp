package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.tenant.BalanceSheet;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.BalanceSheet.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Data
public class BalanceSheetDTO {
  private Long id;
  private LocalDate dated;
  private TransactionType transactionType;
  private Long transactionId;
  private BigDecimal txnAmount;
  private BigDecimal finalAmount;
  private Long customerId;

  public BalanceSheetDTO(BalanceSheet entity) {
    setId(entity.getId());
    setDated(entity.getDated());
    setTransactionType(entity.getTransactionType());
    setTransactionId(entity.getTransactionId());
    setTxnAmount(entity.getTxnAmount());
    setFinalAmount(entity.getFinalAmount());
    setCustomerId(entity.getCustomer().getId());

  }
}
