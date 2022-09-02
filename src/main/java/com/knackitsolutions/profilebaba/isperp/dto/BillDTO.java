package com.knackitsolutions.profilebaba.isperp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class BillDTO {
  private Long id;
  private String billNumber;
  private LocalDate invoiceDate;
  private Long customerId;
  private BigDecimal total;
  private List<BillItemDTO> billItems;
}
