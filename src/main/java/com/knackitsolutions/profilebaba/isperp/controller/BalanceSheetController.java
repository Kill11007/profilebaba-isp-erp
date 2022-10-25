package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.dto.BalanceSheetDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.BalanceSheet.TransactionType;
import com.knackitsolutions.profilebaba.isperp.service.AdjustBalanceService.AdjustedBalanceNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.BalanceSheetService;
import com.knackitsolutions.profilebaba.isperp.service.BillService.BillNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.PaymentService.PaymentNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@CrossOrigin
@RequiredArgsConstructor
@RestController
public class BalanceSheetController {

  private final BalanceSheetService balanceSheetService;

  @GetMapping
  public ResponseEntity<Page<BalanceSheetDTO>> getAll(@RequestParam Optional<Pageable> pageable) {
    Pageable pageRequest = pageable.orElseGet(() -> PageRequest.ofSize(Integer.MAX_VALUE));
    return ResponseEntity.ok(
        balanceSheetService.getAllOnDateSorted(pageRequest).map(BalanceSheetDTO::new));
  }

  @GetMapping("/customers/{customer-id}/balance-sheet")
  public ResponseEntity<Page<BalanceSheetDTO>> getAll(@PathVariable("customer-id") Long customerId,
      @RequestParam Optional<Pageable> pageable) {
    Pageable pageRequest = pageable.orElseGet(() -> PageRequest.ofSize(Integer.MAX_VALUE));
    return ResponseEntity.ok(
        balanceSheetService.getCustomerSheetOnDateSorted(customerId, pageRequest)
            .map(BalanceSheetDTO::new));
  }

  @GetMapping("/balance-sheet/{transaction-id}/transactions/{transaction-type}")
  public ResponseEntity<?> getOne(@PathVariable("transaction-id") Long transactionId,
      @PathVariable("transaction-type") String transactionType)
      throws AdjustedBalanceNotFoundException, PaymentNotFoundException, BillNotFoundException {
    return ResponseEntity.ok(
        balanceSheetService.getTransaction(transactionId, TransactionType.of(transactionType)));
  }

  @DeleteMapping("/balance-sheet/{transaction-id}/transactions/{transaction-type}")
  public ResponseEntity<?> deleteOne(@PathVariable("transaction-id") Long transactionId,
      @PathVariable("transaction-type") String transactionType)
      throws AdjustedBalanceNotFoundException, PaymentNotFoundException, BillNotFoundException {
    balanceSheetService.deleteTransaction(transactionId, TransactionType.of(transactionType));
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/customers/{customer-id}/balance-sheet")
  public ResponseEntity<?> deleteLastTransaction(@PathVariable("customer-id") Long customerId)
      throws AdjustedBalanceNotFoundException, PaymentNotFoundException, BillNotFoundException {
    balanceSheetService.deleteLastTransaction(customerId);
    return ResponseEntity.noContent().build();
  }

}
