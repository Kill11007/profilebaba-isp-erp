package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.entity.tenant.BalanceSheet;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.BalanceSheet.TransactionType;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Transaction;
import com.knackitsolutions.profilebaba.isperp.service.AdjustBalanceService.AdjustedBalanceNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.BillService.BillNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.PaymentService.PaymentNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BalanceSheetService {

  void addTransaction(Transaction transaction)
      throws AdjustedBalanceNotFoundException, PaymentNotFoundException, BillNotFoundException;

  Transaction getLastTransaction(Long customerId)
      throws AdjustedBalanceNotFoundException, PaymentNotFoundException, BillNotFoundException;

  Page<BalanceSheet> getAllOnDateSorted(Pageable pageable);
  Page<BalanceSheet> getCustomerSheetOnDateSorted(Long customerId, Pageable pageable);

  Transaction getTransaction(Long id, TransactionType transactionType)
      throws AdjustedBalanceNotFoundException, BillNotFoundException, PaymentNotFoundException;

  void deleteTransaction(Long id, TransactionType transactionType)
      throws AdjustedBalanceNotFoundException, BillNotFoundException, PaymentNotFoundException;

  void deleteLastTransaction(Long customerId) throws BillNotFoundException, AdjustedBalanceNotFoundException, PaymentNotFoundException;

  BalanceSheet getLastBalanceSheet(Long customerId);
}
