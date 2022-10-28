package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.dto.PaymentDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Customer;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Employee;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Payment;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.EmployeeNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.AdjustBalanceService.AdjustedBalanceNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.BillService.BillNotFoundException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {

  Payment get(Long id) throws PaymentNotFoundException;
  PaymentDTO getDTO(Long id) throws PaymentNotFoundException;

  Payment receive(Payment payment, Employee employee, Customer customer);

  Payment receive(PaymentDTO dto, Long customerId)
      throws EmployeeNotFoundException, AdjustedBalanceNotFoundException, PaymentNotFoundException, BillNotFoundException, CustomerNotFoundException, UserNotFoundException;

  Page<PaymentDTO> allDTO(Pageable pageable);
  Page<Payment> all(Pageable pageable);

  void delete(Long id) throws PaymentNotFoundException;

  class PaymentNotFoundException extends Exception {

  }

}
