package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.dto.AdjustedBalanceDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.AdjustedBalance;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.BillService.BillNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.PaymentService.PaymentNotFoundException;

public interface AdjustBalanceService {

  AdjustedBalance get(Long id) throws AdjustedBalanceNotFoundException;

  AdjustedBalanceDTO getDTO(Long id) throws AdjustedBalanceNotFoundException;

  AdjustedBalance add(Long customerId, AdjustedBalanceDTO dto)
      throws AdjustedBalanceNotFoundException, PaymentNotFoundException, BillNotFoundException, CustomerNotFoundException;

  void delete(Long id) throws AdjustedBalanceNotFoundException;

  class AdjustedBalanceNotFoundException extends Exception {

  }

}
