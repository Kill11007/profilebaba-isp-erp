package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.tenant.BillItem;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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

  public BillItemDTO(BillItem entity) {
    setId(entity.getId());
    setName(entity.getName());
    setQuantity(entity.getQuantity());
    setHsnCode(entity.getHsnCode());
    setDiscount(entity.getDiscount());
    setAdditionalCharges(entity.getAdditionalCharges());
    setGstPercent(entity.getGstPercent());
    setGstAmount(entity.getGstAmount());
    setAmount(entity.getAmount());
  }
}
