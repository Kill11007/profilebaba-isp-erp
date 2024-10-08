package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.dto.PaymentDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.BalanceSheet;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Customer;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Employee;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Payment;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Payment.PaymentModeNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.EmployeeNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.PaymentRepository;
import com.knackitsolutions.profilebaba.isperp.service.AdjustBalanceService.AdjustedBalanceNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.BalanceSheetService;
import com.knackitsolutions.profilebaba.isperp.service.BillService.BillNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.CustomerService;
import com.knackitsolutions.profilebaba.isperp.service.EmployeeService;
import com.knackitsolutions.profilebaba.isperp.service.PaymentService;

import java.math.BigDecimal;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class PaymentServiceImpl implements PaymentService {

  private final PaymentRepository repository;
  private final EmployeeService employeeService;
  private final CustomerService customerService;

  private BalanceSheetService balanceSheetService;

  @Autowired
  public void setBalanceSheetService(BalanceSheetService balanceSheetService) {
    this.balanceSheetService = balanceSheetService;
  }

  @Override
  public Payment get(Long id) throws PaymentNotFoundException {
    return repository.findById(id).orElseThrow(PaymentNotFoundException::new);
  }

  @Override
  public PaymentDTO getDTO(Long id) throws PaymentNotFoundException {
    Payment payment = get(id);
    return new PaymentDTO(payment);
  }

  @Override
  public Payment receive(Payment payment, Employee employee, Customer customer) {
    BalanceSheet lastBalanceSheet = balanceSheetService.getLastBalanceSheet(customer.getId());
    BigDecimal finalPayment = BigDecimal.ZERO;
    if (lastBalanceSheet != null) {
      finalPayment = lastBalanceSheet.getFinalAmount();
    }
    payment.setNetAmount(payment.getPaidAmount().subtract(payment.getDiscount()));
    payment.setRemainingAmount(payment.getFinalAmount(finalPayment));
    payment.setCollectedBy(employee.getName());
    payment.setCustomer(customer);
    payment.setEmployee(employee);
    payment.setPreviousBalance(finalPayment);
    payment.setReceiptNumber(createReceiptNumber(lastBalanceSheet));
    return repository.save(payment);
  }

  @Override
  public Payment receive(PaymentDTO dto, Long customerId)
      throws EmployeeNotFoundException, CustomerNotFoundException, AdjustedBalanceNotFoundException, PaymentNotFoundException, BillNotFoundException, UserNotFoundException {
    //get employeeID, customerId, paidAmount, discount, paymentMode, comment 
    Employee employee = employeeService.findById(dto.getEmployeeId());
    Customer customer = customerService.getCustomerById(customerId);
    Payment payment = new Payment(dto);
    Payment received = receive(payment, employee, customer);
    balanceSheetService.addTransaction(received);
    return received;
  }

  @Override
  public Page<PaymentDTO> allDTO(Pageable pageable) {
    return repository.findAll(pageable).map(PaymentDTO::new);
  }

  @Override
  public Page<Payment> all(Pageable pageable) {
    return repository.findAll(pageable);
  }

  @Override
  public void delete(Long id) throws PaymentNotFoundException {
    log.info("Deleting payment with id: {}", id);
    Payment payment = get(id);
    repository.delete(payment);
    log.info("Deleted payment with id: {}", id);

  }

  private String createReceiptNumber(BalanceSheet lastBalanceSheet) {
    long lastBalanceSheetId = 0L;
    if (lastBalanceSheet != null) {
      lastBalanceSheetId = lastBalanceSheet.getId() + 1;
    }
    return String.valueOf(lastBalanceSheetId + 1);
  }
}
