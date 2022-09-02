package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.dto.BillDTO;
import com.knackitsolutions.profilebaba.isperp.dto.BillItemDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Bill;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.BillItem;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerNotFoundException;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BillService {

  List<Bill> generateBills();

  Page<Bill> getCustomerBills(Long customerId, Pageable pageable) throws CustomerNotFoundException;

  Page<Bill> getBills() throws CustomerNotFoundException;

  Bill get(Long id) throws BillNotFoundException;

  void addBillItems(Long id, List<BillItem> billItems) throws BillNotFoundException;

  void addBillItemsDTO(Long id, List<BillItemDTO> billItems) throws BillNotFoundException;

  BillDTO getDTO(Long id) throws BillNotFoundException;

  BillItem getBillItem(Long id);

  BillItem getBillItemDTO(Long id);

  class BillNotFoundException extends Exception {

    private static final String defaultMessage = "Bill not found with the provide id";
    private static final String cause = "Resource not found in db";
    private static final Supplier<Throwable> defaultCause = () -> new Throwable(cause);

    public BillNotFoundException() {
      super(defaultMessage, defaultCause.get());
    }

    public BillNotFoundException(String message) {
      super(message, defaultCause.get());
    }

    public BillNotFoundException(String message, Throwable cause) {
      super(message, cause);
    }

    public BillNotFoundException(Throwable cause) {
      super(defaultMessage, cause);
    }

  }

}
