package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.main.User;
import com.knackitsolutions.profilebaba.isperp.entity.main.Vendor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VendorDTO {

  private Long vendorId;
  private String phoneNumber;
  private String businessName;
  private Long userId;
  public VendorDTO(Vendor vendor, User user) {
    this.phoneNumber = user.getPhoneNumber();
    this.vendorId = vendor.getId();
    this.businessName = vendor.getBusinessName();
    this.userId = user.getId();
  }

  public VendorDTO(Vendor vendor) {
    this.phoneNumber = vendor.getPhone();
    this.vendorId = vendor.getId();
    this.businessName = vendor.getBusinessName();
    this.userId = vendor.getUserId();
  }
}
