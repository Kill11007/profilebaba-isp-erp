package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.main.User;
import com.knackitsolutions.profilebaba.isperp.entity.main.Vendor;
import com.knackitsolutions.profilebaba.isperp.entity.main.VendorPlan;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
public class VendorDTO implements ProfileName{

  private Long vendorId;
  private String phoneNumber;
  private String businessName;
  private Long userId;
  private IspPlanDTO plan;
  public VendorDTO(Vendor vendor, User user) {
    this(vendor);
    this.userId = user.getId();
  }

  public VendorDTO(Vendor vendor) {
    this.phoneNumber = vendor.getPhone();
    this.vendorId = vendor.getId();
    this.businessName = vendor.getBusinessName();
    this.userId = vendor.getUserId();
    Optional<VendorPlan> activeVendorPlan = vendor.getVendorPlans()
            .stream()
            .filter(vendorPlan -> vendorPlan.getEndDateTime() == null)
            .findFirst();
    activeVendorPlan.ifPresent(vendorPlan -> setPlan(new IspPlanDTO(vendorPlan.getPlan())));
  }

  @Override
  public String getName() {
    return this.businessName;
  }
}
