package com.knackitsolutions.profilebaba.isperp.mapper;

import com.knackitsolutions.profilebaba.isperp.dto.PlanDTO;
import com.knackitsolutions.profilebaba.isperp.entity.Plan;
import org.springframework.stereotype.Component;

@Component
public class PlanMapper {

  // Not including ID
  public Plan dtoToEntity(PlanDTO dto) {
    return Plan.builder().active(dto.getActive())
        .additionalCharge(dto.getAdditionalCharge()).discount(dto.getDiscount())
        .gstPercent(dto.getGstPercent()).hsnCode(dto.getHsnCode()).productCode(dto.getProductCode())
        .name(dto.getName()).price(dto.getPrice()).build();
  }

  public Plan dtoToEntityWithId(PlanDTO dto) {
    Plan plan = dtoToEntity(dto);
    plan.setId(dto.getId());
    return plan;
  }

  public PlanDTO entityToDTO(Plan plan) {
    return PlanDTO.builder().active(plan.getActive()).additionalCharge(plan.getAdditionalCharge())
        .discount(plan.getDiscount()).gstPercent(plan.getGstPercent()).hsnCode(plan.getHsnCode())
        .productCode(plan.getProductCode()).id(plan.getId()).price(plan.getPrice())
        .name(plan.getName()).build();
  }

}
