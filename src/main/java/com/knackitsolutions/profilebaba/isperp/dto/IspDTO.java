package com.knackitsolutions.profilebaba.isperp.dto;

import lombok.Data;

@Data
public class IspDTO {

  private UserDTO userInfo;
  private TenantDTO tenantInfo;
  private VendorDTO vendorInfo;

  public IspDTO(UserDTO userInfo, TenantDTO tenantInfo, VendorDTO vendorInfo) {
    this.userInfo = userInfo;
    this.tenantInfo = tenantInfo;
    this.vendorInfo = vendorInfo;
  }
}
