package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.tenant.AdjustedBalance;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdjustedBalanceDTO {
  private Long id;
  private LocalDate dated;
  private Long customerId;
  private String details;
  private BigDecimal adjustedBalance;
  private BigDecimal previousBalance;
  private BigDecimal grandTotal;

  public AdjustedBalanceDTO(AdjustedBalance entity){
    setId(entity.getId());
    setDated(entity.getDated());
    setCustomerId(entity.getCustomerId());
    setDetails(entity.getRemark());
    setAdjustedBalance(entity.getAmount());
    setPreviousBalance(entity.getPreviousBalance());
    setGrandTotal(entity.getGrandTotal());
  }
}
