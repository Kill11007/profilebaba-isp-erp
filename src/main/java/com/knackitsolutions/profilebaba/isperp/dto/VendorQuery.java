package com.knackitsolutions.profilebaba.isperp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorQuery {
  private String phone;
  private String businessName;
  private Long vendorId;
  private Long userId;
}
