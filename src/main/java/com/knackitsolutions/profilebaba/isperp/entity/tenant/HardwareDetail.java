package com.knackitsolutions.profilebaba.isperp.entity.tenant;

import com.knackitsolutions.profilebaba.isperp.dto.HardwareDetailDTO;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "hardware_details")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HardwareDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "customer_id", nullable = false)
  private Customer customer;

  private String router;
  private String ip;
  private String mac;
  private String membershipNumber;

  public void update(HardwareDetailDTO dto) {
    this.setIp(dto.getIp());
    this.setMembershipNumber(dto.getMembershipNumber());
    this.setMac(dto.getMac());
    this.setRouter(dto.getRouter());
  }

  //Without Customer and Id
  public HardwareDetail(HardwareDetailDTO dto) {
    if (dto == null) {
      return;
    }
    this.setIp(dto.getIp());
    this.setMembershipNumber(dto.getMembershipNumber());
    this.setMac(dto.getMac());
    this.setRouter(dto.getRouter());
  }
}
