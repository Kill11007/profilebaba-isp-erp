package com.knackitsolutions.profilebaba.isperp.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class BillItemDTO {
  private Long id;
  private String name;
  private Integer quantity;
  private String hsnCode;
  private BigDecimal discount;
  private BigDecimal additionalCharges;
  private Integer gstPercent;
  private BigDecimal gstAmount;
  private BigDecimal amount;
}
