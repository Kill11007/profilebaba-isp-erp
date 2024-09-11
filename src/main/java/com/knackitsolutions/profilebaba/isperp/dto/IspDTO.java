package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.main.Tenant;
import com.knackitsolutions.profilebaba.isperp.entity.main.Vendor;
import com.knackitsolutions.profilebaba.isperp.entity.main.VendorPlan;
import com.knackitsolutions.profilebaba.isperp.enums.UserType;
import lombok.Data;

import java.util.Optional;

@Data
public class IspDTO {
  private String tenantId;
  private String tenantDB;
  private String tenantDBUrl;
  private Long userId;
  private UserType userType;
  private Long vendorId;
  private String phoneNumber;
  private String businessName;
  private IspPlanDTO plan;
//  private UserDTO userInfo;
//  private TenantDTO tenantInfo;
//  private VendorDTO vendorInfo;

  public IspDTO(Tenant tenantInfo, Vendor vendorInfo) {
    this.phoneNumber = vendorInfo.getPhone();
    this.vendorId = vendorInfo.getId();
    this.businessName = vendorInfo.getBusinessName();
    this.userId = vendorInfo.getUserId();
    Optional<VendorPlan> activeVendorPlan = vendorInfo.getVendorPlans()
            .stream()
            .filter(vendorPlan -> vendorPlan.getEndDateTime() == null)
            .findFirst();
    activeVendorPlan.ifPresent(vendorPlan -> setPlan(new IspPlanDTO(vendorPlan.getPlan())));

    //tenant
    this.setTenantId(tenantInfo.getTenantId());
    this.setTenantDB(tenantInfo.getDb());
    this.setTenantDBUrl(tenantInfo.getUrl());
  }
}
