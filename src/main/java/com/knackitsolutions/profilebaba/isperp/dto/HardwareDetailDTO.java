package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.tenant.HardwareDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HardwareDetailDTO {

  private Long id;
  private Long customerId;
  private String router;
  private String ip;
  private String mac;
  private String membershipNumber;

  public HardwareDetailDTO(HardwareDetail hardware) {
    if (hardware == null) {
      return;
    }
    this.setId(hardware.getId());
    this.setCustomerId(hardware.getCustomer().getId());
    this.setIp(hardware.getIp());
    this.setMac(hardware.getMac());
    this.setRouter(hardware.getRouter());
    this.setMembershipNumber(hardware.getMembershipNumber());
  }
}
