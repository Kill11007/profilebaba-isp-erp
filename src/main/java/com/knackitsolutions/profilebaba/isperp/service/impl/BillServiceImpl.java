package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.dto.BillDTO;
import com.knackitsolutions.profilebaba.isperp.dto.BillItemDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Bill;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.BillItem;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Customer;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Subscription;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerNotFoundException;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.BillItemRepository;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.BillRepository;
import com.knackitsolutions.profilebaba.isperp.service.BillService;
import com.knackitsolutions.profilebaba.isperp.service.CustomerService;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

  private final BillRepository billRepository;
  private final BillItemRepository billItemRepository;
  private final CustomerService customerService;

  @Override
  public List<Bill> generateBills() {
    List<Customer> customers = customerService.all();
    for (Customer customer : customers) {
      Set<Bill> bills = customer.getBills();

      Set<Subscription> subscriptions = customer.getSubscriptions();

    }
    return null;
  }


  //Pre Paid
  private boolean hasNextBillGenerated(Customer customer, Subscription subscription) {
    Set<Bill> bills = customer.getBills();
    for (Bill bill : bills) {
      LocalDate invoiceDate = bill.getInvoiceDate();

    }
    return false;
  }

  @Override
  public Page<Bill> getCustomerBills(Long customerId, Pageable pageable)
      throws CustomerNotFoundException {
    return null;
  }

  @Override
  public Page<Bill> getBills() throws CustomerNotFoundException {
    return null;
  }

  @Override
  public Bill get(Long id) throws BillNotFoundException {
    return null;
  }

  @Override
  public void addBillItems(Long id, List<BillItem> billItems) throws BillNotFoundException {

  }

  @Override
  public void addBillItemsDTO(Long id, List<BillItemDTO> billItems) throws BillNotFoundException {

  }

  @Override
  public BillDTO getDTO(Long id) throws BillNotFoundException {
    return null;
  }

  @Override
  public BillItem getBillItem(Long id) {
    return null;
  }

  @Override
  public BillItem getBillItemDTO(Long id) {
    return null;
  }
}
