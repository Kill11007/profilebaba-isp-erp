package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.dto.BillDTO;
import com.knackitsolutions.profilebaba.isperp.dto.BillItemDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Bill;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.AdjustBalanceService.AdjustedBalanceNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.BillService;
import com.knackitsolutions.profilebaba.isperp.service.BillService.BillNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.PaymentService.PaymentNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/bills")
@CrossOrigin
@RequiredArgsConstructor
@RestController
public class BillController {

  private final BillService billService;

  @GetMapping
  public ResponseEntity<Page<BillDTO>> getBills() {
    return ResponseEntity.ok(billService.getBills().map(BillDTO::new));
  }

  @GetMapping("/{customer-id}")
  public ResponseEntity<Page<BillDTO>> getBills(@PathVariable("customer-id") Long customerId)
      throws CustomerNotFoundException {
    return ResponseEntity.ok(
        billService.getCustomerBills(customerId, PageRequest.ofSize(10)).map(BillDTO::new));
  }

  @PutMapping("/{customer-id}/add-item")
  public ResponseEntity<Void> addItemOnBill(@PathVariable("customer-id") Long customerId,
      @RequestBody BillItemDTO dto)
      throws AdjustedBalanceNotFoundException, PaymentNotFoundException, BillNotFoundException {
    billService.addItemOnBill(customerId, dto);
    return ResponseEntity.noContent().build();
  }

  @PostMapping
  public ResponseEntity<Void> generateBill(@RequestBody BillDTO billDTO)
      throws CustomerNotFoundException, AdjustedBalanceNotFoundException, PaymentNotFoundException, BillNotFoundException {
    List<Bill> bills = billService.generateBillManually(billDTO);
    return ResponseEntity.noContent().build();
  }
}
