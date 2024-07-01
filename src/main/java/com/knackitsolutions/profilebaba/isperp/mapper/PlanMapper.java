package com.knackitsolutions.profilebaba.isperp.mapper;

import com.knackitsolutions.profilebaba.isperp.dto.PlanDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.InternetPlan;
import org.springframework.stereotype.Component;

@Component
public class PlanMapper {

  // Not including ID
  public InternetPlan dtoToEntity(PlanDTO dto) {
    return InternetPlan.builder().active(dto.getActive())
        .additionalCharge(dto.getAdditionalCharge()).discount(dto.getDiscount())
        .gstPercent(dto.getGstPercent()).hsnCode(dto.getHsnCode()).productCode(dto.getProductCode())
        .name(dto.getName()).price(dto.getPrice()).build();
  }

  public InternetPlan dtoToEntityWithId(PlanDTO dto) {
    InternetPlan internetPlan = dtoToEntity(dto);
    internetPlan.setId(dto.getId());
    return internetPlan;
  }

  public PlanDTO entityToDTO(InternetPlan internetPlan) {
    return PlanDTO.builder().active(internetPlan.getActive()).additionalCharge(internetPlan.getAdditionalCharge())
        .discount(internetPlan.getDiscount()).gstPercent(internetPlan.getGstPercent()).hsnCode(
            internetPlan.getHsnCode())
        .productCode(internetPlan.getProductCode()).id(internetPlan.getId()).price(internetPlan.getPrice())
        .name(internetPlan.getName()).build();
  }

}
