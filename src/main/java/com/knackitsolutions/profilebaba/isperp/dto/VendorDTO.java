package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.Vendor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VendorDTO {

  private Long vendorId;
  private String phoneNumber;
  private String businessName;
  public VendorDTO(Vendor vendor) {
    this.phoneNumber = vendor.getPhoneNumber();
    this.vendorId = vendor.getId();
    this.businessName = vendor.getBusinessName();
  }
}
