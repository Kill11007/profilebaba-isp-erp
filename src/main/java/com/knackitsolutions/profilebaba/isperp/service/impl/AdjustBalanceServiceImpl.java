package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.dto.AdjustedBalanceDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.AdjustedBalance;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.BalanceSheet;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Customer;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerNotFoundException;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.AdjustedBalanceRepository;
import com.knackitsolutions.profilebaba.isperp.service.AdjustBalanceService;
import com.knackitsolutions.profilebaba.isperp.service.BalanceSheetService;
import com.knackitsolutions.profilebaba.isperp.service.BillService.BillNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.CustomerService;
import com.knackitsolutions.profilebaba.isperp.service.PaymentService.PaymentNotFoundException;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdjustBalanceServiceImpl implements AdjustBalanceService {

  private final AdjustedBalanceRepository repository;
  private final CustomerService customerService;

  private BalanceSheetService balanceSheetService;

  @Autowired
  public void setBalanceSheetService(
      @Lazy BalanceSheetService balanceSheetService) {
    this.balanceSheetService = balanceSheetService;
  }

  @Override
  public AdjustedBalance get(Long id) throws AdjustedBalanceNotFoundException {
    return repository.findById(id).orElseThrow(AdjustedBalanceNotFoundException::new);
  }

  @Override
  public AdjustedBalanceDTO getDTO(Long id) throws AdjustedBalanceNotFoundException {
    return new AdjustedBalanceDTO(this.get(id));
  }

  @Override
  public AdjustedBalance add(Long customerId, AdjustedBalanceDTO dto)
      throws AdjustedBalanceNotFoundException, PaymentNotFoundException, BillNotFoundException, CustomerNotFoundException {
    Customer customer = customerService.getCustomerById(customerId);
    BalanceSheet lastBalanceSheet = balanceSheetService.getLastBalanceSheet(customer.getId());
    AdjustedBalance adjustedBalance = new AdjustedBalance(dto);
    BigDecimal finalAmount = lastBalanceSheet.getFinalAmount();
    adjustedBalance.setPreviousBalance(finalAmount);
    BigDecimal balance = dto.getAdjustedBalance();
    BigDecimal amount = finalAmount.compareTo(balance) < 0 ? balance.subtract(finalAmount)
        : finalAmount.subtract(balance);
    adjustedBalance.setAmount(amount);
    adjustedBalance.setCustomer(customer);
    adjustedBalance.setGrandTotal(dto.getAdjustedBalance());
    AdjustedBalance save = repository.save(adjustedBalance);
    balanceSheetService.addTransaction(save);
    return save;
  }

  @Override
  public void delete(Long id) throws AdjustedBalanceNotFoundException {
    AdjustedBalance adjustedBalance = get(id);
    repository.delete(adjustedBalance);
  }
}
