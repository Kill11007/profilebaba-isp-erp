package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.entity.tenant.BalanceSheet;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.BalanceSheet.TransactionType;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Transaction;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.BalanceSheetRepository;
import com.knackitsolutions.profilebaba.isperp.service.AdjustBalanceService;
import com.knackitsolutions.profilebaba.isperp.service.AdjustBalanceService.AdjustedBalanceNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.BalanceSheetService;
import com.knackitsolutions.profilebaba.isperp.service.BillService;
import com.knackitsolutions.profilebaba.isperp.service.BillService.BillNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.PaymentService;
import com.knackitsolutions.profilebaba.isperp.service.PaymentService.PaymentNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class BalanceSheetServiceImpl implements BalanceSheetService {

  private final BalanceSheetRepository repository;
  private BillService billService;
  private PaymentService paymentService;

  private AdjustBalanceService adjustBalanceService;

  public BillService getBillService() {
    return billService;
  }


  @Autowired
  @Lazy
  public void setBillService(BillService billService) {
    this.billService = billService;
  }

  public PaymentService getPaymentService() {
    return paymentService;
  }


  @Autowired
  @Lazy
  public void setPaymentService(
      PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  public AdjustBalanceService getAdjustBalanceService() {
    return adjustBalanceService;
  }


  @Autowired
  @Lazy
  public void setAdjustBalanceService(
      AdjustBalanceService adjustBalanceService) {
    this.adjustBalanceService = adjustBalanceService;
  }

  @Override
  public void addTransaction(Transaction transaction){
    BalanceSheet balanceSheet = new BalanceSheet(transaction,
        Optional.ofNullable(getLastBalanceSheet(transaction.getCustomerId()))
            .map(BalanceSheet::getFinalAmount).orElse(
                BigDecimal.ZERO));
    log.info("Balance sheet: {}", balanceSheet);
    repository.save(balanceSheet);
  }

  public BalanceSheet getLastBalanceSheet(Long customerId) {
    List<BalanceSheet> balanceSheets = repository.findTop1ByCustomerId(customerId,
        PageRequest.of(0, 1, Sort.by(Direction.DESC, "dated")));
    if (balanceSheets.size() > 0) {
      return balanceSheets.get(0);
    }
    return null;
  }

  @Override
  public Transaction getLastTransaction(Long customerId)
      throws AdjustedBalanceNotFoundException, PaymentNotFoundException, BillNotFoundException {
    BalanceSheet balanceSheet = getLastBalanceSheet(customerId);
    return getLastTransaction(balanceSheet);
  }

  private Transaction getLastTransaction(BalanceSheet balanceSheet)
      throws AdjustedBalanceNotFoundException, BillNotFoundException, PaymentNotFoundException {
    TransactionType transactionType = balanceSheet.getTransactionType();
    Long transactionId = balanceSheet.getTransactionId();
    if (transactionType == TransactionType.ADJUSTED_BALANCE) {
      return adjustBalanceService.get(transactionId);
    } else if (transactionType == TransactionType.BILL) {
      return billService.get(transactionId);
    } else if (transactionType == TransactionType.PAYMENT) {
      return paymentService.get(transactionId);
    }
    return null;
  }

  @Override
  public Page<BalanceSheet> getAllOnDateSorted(Pageable pageable) {
    return repository.findAll(getAllSortedOnDate(pageable));
  }

  @Override
  public Page<BalanceSheet> getCustomerSheetOnDateSorted(Long customerId, Pageable pageable) {
    return repository.findByCustomerId(customerId, getAllSortedOn(pageable,
        Sort.by(Direction.DESC, "id")));
  }

  private PageRequest getAllSortedOnDate(Pageable pageable) {
    return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
        Sort.by(Direction.DESC, "dated"));
  }

  private PageRequest getAllSortedOn(Pageable pageable, Sort sort) {
    return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
  }

  @Override
  public Transaction getTransaction(Long transactionId, TransactionType transactionType)
      throws AdjustedBalanceNotFoundException, BillNotFoundException, PaymentNotFoundException {
    if (transactionType == TransactionType.ADJUSTED_BALANCE) {
      return adjustBalanceService.get(transactionId);
    } else if (transactionType == TransactionType.BILL) {
      return billService.get(transactionId);
    } else if (transactionType == TransactionType.PAYMENT) {
      return paymentService.get(transactionId);
    }
    return null;
  }

  @Override
  public void deleteTransaction(Long id, TransactionType transactionType)
      throws AdjustedBalanceNotFoundException, BillNotFoundException, PaymentNotFoundException {
    if (transactionType == TransactionType.BILL) {
      billService.delete(id);
    } else if (transactionType == TransactionType.ADJUSTED_BALANCE) {
      adjustBalanceService.delete(id);
    } else if (transactionType == TransactionType.PAYMENT) {
      paymentService.delete(id);
    }
    BalanceSheet balanceSheet = repository.findByTransactionIdAndTransactionType(
        id, transactionType);
    repository.delete(balanceSheet);
  }

  @Override
  public void deleteLastTransaction(Long customerId)
      throws BillNotFoundException, AdjustedBalanceNotFoundException, PaymentNotFoundException {
    BalanceSheet lastBalanceSheet = getLastBalanceSheet(customerId);
    TransactionType transactionType = lastBalanceSheet.getTransactionType();
    Long id = lastBalanceSheet.getTransactionId();
    if (transactionType == TransactionType.BILL) {
      billService.delete(id);
    } else if (transactionType == TransactionType.ADJUSTED_BALANCE) {
      adjustBalanceService.delete(id);
    } else if (transactionType == TransactionType.PAYMENT) {
      paymentService.delete(id);
    }
    BalanceSheet balanceSheet = repository.findByTransactionIdAndTransactionType(
        id, transactionType);
    repository.delete(balanceSheet);
  }
}
