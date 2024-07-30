package com.knackitsolutions.profilebaba.isperp.entity.tenant;

import com.knackitsolutions.profilebaba.isperp.entity.tenant.BalanceSheet.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface Transaction {

  BigDecimal getTotalAmount();
  BigDecimal getFinalAmount(BigDecimal lastAmount);

  TransactionType getTransactionType();

  Long getTransactionId();

  LocalDateTime getTransactionDate();

  Long getCustomerId();

  Customer getCustomer();

}
