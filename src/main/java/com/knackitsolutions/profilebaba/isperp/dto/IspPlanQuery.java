package com.knackitsolutions.profilebaba.isperp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IspPlanQuery {
  private String name;
  private Long planId;
  private String description;
  private String planType;

  public IspPlanSpecification toSpecification() {
    return new IspPlanSpecification(this);
  }
}
