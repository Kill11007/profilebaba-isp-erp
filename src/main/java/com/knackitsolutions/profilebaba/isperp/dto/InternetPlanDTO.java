package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.tenant.InternetPlan;
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
public class InternetPlanDTO {

  public InternetPlanDTO(InternetPlan internetPlan) {
    this.setActive(internetPlan.getActive() == null || internetPlan.getActive());
    this.setDiscount(internetPlan.getDiscount());
    this.setAdditionalCharge(internetPlan.getAdditionalCharge());
    this.setGstPercent(internetPlan.getGstPercent());
    this.setHsnCode(internetPlan.getHsnCode());
    this.setProductCode(internetPlan.getProductCode());
    this.setName(internetPlan.getName());
    this.setPrice(internetPlan.getPrice());
    this.setId(internetPlan.getId());
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
