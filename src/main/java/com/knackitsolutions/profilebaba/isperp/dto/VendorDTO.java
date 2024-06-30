package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.main.User;
import com.knackitsolutions.profilebaba.isperp.entity.main.Vendor;
import com.knackitsolutions.profilebaba.isperp.entity.main.VendorPlan;
import com.knackitsolutions.profilebaba.isperp.exception.PlanNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VendorDTO {

  private Long vendorId;
  private String phoneNumber;
  private String businessName;
  private Long userId;
  private IspPlanDTO plan;
  private MenuItem menu;
  public VendorDTO(Vendor vendor, User user) {
    this(vendor);
    this.userId = user.getId();
  }

  public VendorDTO(Vendor vendor) {
    this.phoneNumber = vendor.getPhone();
    this.vendorId = vendor.getId();
    this.businessName = vendor.getBusinessName();
    this.userId = vendor.getUserId();
    this.plan = vendor.getVendorPlans()
        .stream()
        .findFirst()
        .map(VendorPlan::getPlan)
        .map(IspPlanDTO::new).orElseThrow(PlanNotFoundException::new);
    this.menu = new MenuItem(this.plan.getPermissionDTOS());
  }
}
