package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.Plan;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanDTO {

  public PlanDTO(Plan plan) {
    this.setActive(plan.getActive());
    this.setDiscount(plan.getDiscount());
    this.setAdditionalCharge(plan.getAdditionalCharge());
    this.setGstPercent(plan.getGstPercent());
    this.setHsnCode(plan.getHsnCode());
    this.setProductCode(plan.getProductCode());
    this.setName(plan.getName());
    this.setPrice(plan.getPrice());
    this.setId(plan.getId());
  }

  @Nullable
  private Long id;
  private String name;

  private String productCode;
  private String hsnCode;

  private BigDecimal gstPercent;

  private BigDecimal price;

  private BigDecimal discount;
  private BigDecimal additionalCharge;

  private Boolean active;
}
