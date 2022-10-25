package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.dto.BillDTO;
import com.knackitsolutions.profilebaba.isperp.dto.BillItemDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.BalanceSheet;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.BalanceSheet.TransactionType;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Bill;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.BillItem;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Customer;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Subscription;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Subscription.SubscriptionStatus;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Transaction;
import com.knackitsolutions.profilebaba.isperp.enums.BillType;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerNotFoundException;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.BillItemRepository;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.BillRepository;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.SubscriptionRepository;
import com.knackitsolutions.profilebaba.isperp.service.AdjustBalanceService.AdjustedBalanceNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.BalanceSheetService;
import com.knackitsolutions.profilebaba.isperp.service.BillService;
import com.knackitsolutions.profilebaba.isperp.service.CustomerService;
import com.knackitsolutions.profilebaba.isperp.service.PaymentService.PaymentNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class BillServiceImpl implements BillService {

  private final BillRepository billRepository;
  private final BillItemRepository billItemRepository;
  private final CustomerService customerService;
  private BalanceSheetService balanceSheetService;
  private final SubscriptionRepository subscriptionRepository;

  public BalanceSheetService getBalanceSheetService() {
    return balanceSheetService;
  }

  @Autowired
  public void setBalanceSheetService(
      BalanceSheetService balanceSheetService) {
    this.balanceSheetService = balanceSheetService;
  }

  @Override
  public List<Bill> generateBills() {
    List<Bill> bills = new ArrayList<>();
    List<Customer> customers = customerService.all();
    for (Customer customer : customers) {
      if (customer.getBillingDetail().getBillType() == BillType.POST_PAID) {
        Set<Subscription> subscriptions = customer.getSubscriptions();
        for (Subscription subscription : subscriptions) {
          if (subscription.getEndDate().isBefore(LocalDate.now())) {
            Bill bill = generateBill(customer, subscription);
            Bill save = billRepository.save(bill);
            bills.add(save);
          }
        }
      }
    }
    return bills;
  }

  private Bill generateBill(Customer customer, Subscription subscription) {
    Bill bill = new Bill();
    bill.setFromDate(subscription.getStartDate());
    bill.setToDate(subscription.getEndDate());
    bill.setCustomer(customer);
    bill.setInvoiceDate(LocalDate.now());
    bill.setUpdatedDate(LocalDate.now());
    bill.setBillNumber(generateBillNo(customer.getId(), bill.getUpdatedDate()));
    BigDecimal subscriptionRate = subscription.getPlan().getPrice();
    if (subscription.getFixedBillAmountPerMonth() != null) {
      subscriptionRate = subscription.getFixedBillAmountPerMonth();
    }
    List<BillItem> billItems = new ArrayList<>();
    BillItem billItem = new BillItem();
    billItem.setBill(bill);
    billItem.setAmount(subscriptionRate);
    billItem.setItemId(subscription.getId());
    billItem.setCreatedDate(LocalDateTime.now());
    billItem.setQuantity(1);
    billItem.setName(subscription.getPlan().getName());
    bill.setTotal(subscriptionRate);
    billItems.add(billItem);
    bill.setBillItems(billItems);
    return bill;
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
  public Page<Bill> getBills() {
    return billRepository.findAll(PageRequest.ofSize(10));
  }

  @Override
  public Bill get(Long id) throws BillNotFoundException {
    return billRepository.findById(id).orElseThrow(BillNotFoundException::new);
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

  @Override
  public List<Bill> getCustomerLastBill(Long customerId) {
    return billRepository.findTop1ByCustomer_Id(customerId,
        PageRequest.of(0, 1, Sort.by(Direction.DESC, "invoiceDate")));
  }

  @Override
  public void addItemOnBill(Long customerId, BillItemDTO dto)
      throws AdjustedBalanceNotFoundException, PaymentNotFoundException, BillNotFoundException {
    List<Bill> bills = billRepository.findTop1ByCustomer_Id(customerId,
        PageRequest.of(0, 1, Sort.by(Direction.DESC, "id")));
    Bill lastBill = bills.get(0);
    BillItem newBillItem = new BillItem();
    newBillItem.setName(dto.getName());
    newBillItem.setAmount(dto.getAmount());
    newBillItem.setQuantity(1);
    newBillItem.setBill(lastBill);
    newBillItem.setCreatedDate(LocalDateTime.now());
    lastBill.addBillItem(newBillItem);
    BalanceSheet lastBalanceSheet = balanceSheetService.getLastBalanceSheet(customerId);
    if (lastBalanceSheet.getTransactionType() != TransactionType.BILL) {
      throw new BillNotFoundException(
          "Last transaction type is not a bill. Can not update any other transaction type.");
    }
    billItemRepository.save(newBillItem);
    Bill save = billRepository.save(lastBill);
    balanceSheetService.updateBalanceSheet(save);
  }

  @Override
  public List<Bill> generateBillManually(BillDTO dto)
      throws CustomerNotFoundException, AdjustedBalanceNotFoundException, PaymentNotFoundException, BillNotFoundException {
    List<Bill> bills = new ArrayList<>();
    Customer customer = customerService.getCustomerById(dto.getCustomerId());
    for (Subscription subscription : customer.getSubscriptions()) {
      if(subscription.getStatus() == SubscriptionStatus.IN_ACTIVE)
        continue;
      Bill bill = generateBill(customer, subscription);
      Bill save = billRepository.save(bill);
      billItemRepository.saveAll(bill.getBillItems());
      balanceSheetService.addTransaction(bill);
      subscription.updateStartDateAndEndDate();
      subscriptionRepository.save(subscription);
      bills.add(save);
    }
    return bills;
  }

  private String generateBillNo(Long customerId, LocalDate date) {
    String billNo = "ISP";
    int requiredZeros = 5 - customerId.toString().length();
    String zeros = Stream.generate(() -> "0").limit(requiredZeros)
        .collect(Collectors.joining());
    billNo = billNo + formattedDate(date, "YYYYMMdd") + zeros + customerId;
    log.info("Bill No: {}", billNo);
    return billNo;
  }

  private String formattedDate(LocalDate date, String format) {
    return DateTimeFormatter.ofPattern(format).format(date);
  }

  @Override
  public void delete(Long id) throws BillNotFoundException {
    Bill bill = get(id);
    billItemRepository.deleteAll(bill.getBillItems());
    billRepository.delete(bill);
  }
}
