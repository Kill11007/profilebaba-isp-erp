package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.main.IspPlan;
import com.knackitsolutions.profilebaba.isperp.entity.main.IspPlanPermission;
import com.knackitsolutions.profilebaba.isperp.enums.PlanType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IspPlanDTO {
  private Long id;
  private String name;
  private String planDescription;
  private PlanType planType;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;
  private BigDecimal rate;
  private List<Long> permissionIds;
  private List<PermissionDTO> permissionDTOS;
  public IspPlanDTO(IspPlan entity) {
    setId(entity.getId());
    setName(entity.getName());
    setPlanType(entity.getPlanType());
    setPlanDescription(entity.getPlanDescription());
    setCreatedDate(entity.getCreatedDateTime());
    setUpdatedDate(entity.getUpdateDateTime());
    setRate(entity.getRate());
    setPermissionIds(entity.getIspPlanPermissions().stream().map(IspPlanPermission::getId).collect(
        Collectors.toList()));
    setPermissionDTOS(entity.getIspPlanPermissions().stream().map(IspPlanPermission::getPermission).map(PermissionDTO::new).collect(
        Collectors.toList()));
  }

}
