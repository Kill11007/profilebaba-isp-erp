package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.dto.AdjustedBalanceDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.AdjustedBalance;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.AdjustBalanceService;
import com.knackitsolutions.profilebaba.isperp.service.AdjustBalanceService.AdjustedBalanceNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.BillService.BillNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.PaymentService.PaymentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AdjustedBalanceController {

  private final AdjustBalanceService service;

  @PostMapping("/customers/{customer-id}/adjusted-balance")
  public ResponseEntity<Void> add(@PathVariable("customer-id") Long customerId,
      @RequestBody AdjustedBalanceDTO dto)
      throws AdjustedBalanceNotFoundException, PaymentNotFoundException, BillNotFoundException, CustomerNotFoundException {
    AdjustedBalance add = service.add(customerId, dto);
    return ResponseEntity.noContent().build();
  }
}
