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
  public VendorDTO(Vendor vendor, User user) {
    this.phoneNumber = user.getPhoneNumber();
    this.vendorId = vendor.getId();
    this.businessName = vendor.getBusinessName();
  }
}
