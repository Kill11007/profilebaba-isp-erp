package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.tenant.Bill;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillDTO {
  private Long id;
  private String billNumber;
  private LocalDate invoiceDate;
  private Long customerId;
  private BigDecimal total;
  private List<BillItemDTO> billItems;

  public BillDTO(Bill bill) {
    setBillNumber(bill.getBillNumber());
    setId(bill.getId());
    setCustomerId(bill.getCustomerId());
    setTotal(bill.getTotal());
    setInvoiceDate(bill.getInvoiceDate());
    setBillItems(bill.getBillItems().stream().map(BillItemDTO::new).collect(Collectors.toList()));

  }
}
