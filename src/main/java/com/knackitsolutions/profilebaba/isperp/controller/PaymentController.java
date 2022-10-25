package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.dto.PaymentDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Payment;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.EmployeeNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.AdjustBalanceService.AdjustedBalanceNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.BillService.BillNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.PaymentService;
import com.knackitsolutions.profilebaba.isperp.service.PaymentService.PaymentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@CrossOrigin
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping("/customers/{customer-id}/payments")
  public ResponseEntity<Void> pay(@RequestBody PaymentDTO paymentDTO,
      @PathVariable("customer-id") Long customerId, UriComponentsBuilder uriBuilder)
      throws EmployeeNotFoundException, AdjustedBalanceNotFoundException, PaymentNotFoundException, CustomerNotFoundException, BillNotFoundException {
    Payment receive = paymentService.receive(paymentDTO, customerId);
    return ResponseEntity.created(
        uriBuilder.path("/payments/{id}").buildAndExpand(receive.getId()).toUri()).build();
  }

  @GetMapping("/payments/{payment-id}")
  public ResponseEntity<PaymentDTO> get(@PathVariable("payment-id") Long paymentId)
      throws PaymentNotFoundException {
    return ResponseEntity.ok(paymentService.getDTO(paymentId));
  }
}
