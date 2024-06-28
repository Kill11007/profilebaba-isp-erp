package com.knackitsolutions.profilebaba.isperp.dto;

import lombok.Data;

@Data
public class IspSpecification {
  private VendorSpecification vendorSpecification;
  private UserSpecification userSpecification;
  private TenantSpecification tenantSpecification;

  public IspSpecification(IspQuery ispQuery) {
    vendorSpecification = new VendorSpecification(new VendorQuery(ispQuery.getPhone(),
        ispQuery.getBusinessName(), ispQuery.getVendorId(), ispQuery.getUserId()));
    if (ispQuery.getTenantId() != null) {
      tenantSpecification = new TenantSpecification(ispQuery.getTenantId());
    }
  }
}
