package com.knackitsolutions.profilebaba.isperp.entity.tenant;

import com.knackitsolutions.profilebaba.isperp.dto.CustomerDTO;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.apache.commons.collections4.CollectionUtils;

@Entity
@Table(name = "customers")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  private String billingName;
  private String billingArea;

  private String primaryMobileNo;
  private String secondaryMobileNo;
  private String email;
  private BigDecimal securityDeposit;
  private String address;
  private String gstNo;
  private String customerCode;
  private String remark;

  @Column(nullable = false)
  private Boolean active;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
  @Exclude
  private Set<HardwareDetail> hardwareDetail;

  @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private BillingDetail billingDetail;

  @OneToMany(mappedBy = "customer")
  @Exclude
  private Set<Subscription> subscriptions;

  public void update(CustomerDTO dto) {
    setName(dto.getName());
    setBillingName(dto.getBillingName());
    setBillingArea(dto.getBillingArea());
    setPrimaryMobileNo(dto.getPrimaryMobileNo());
    setSecondaryMobileNo(dto.getSecondaryMobileNo());
    setEmail(dto.getEmail());
    setSecurityDeposit(dto.getSecurityDeposit());
    setAddress(dto.getAddress());
    setGstNo(dto.getGstNo());
    setCustomerCode(dto.getCustomerCode());
    setRemark(dto.getRemark());
    this.getBillingDetail().update(dto.getBillingDetail());
  }

  public Customer(CustomerDTO dto) {
    setName(dto.getName());
    setBillingName(dto.getBillingName());
    setBillingArea(dto.getBillingArea());
    setBillingDetail(new BillingDetail(dto.getBillingDetail()));
    setPrimaryMobileNo(dto.getPrimaryMobileNo());
    setSecondaryMobileNo(dto.getSecondaryMobileNo());
    setEmail(dto.getEmail());
    setSecurityDeposit(dto.getSecurityDeposit());
    setActive(dto.getActive() == null || dto.getActive());
    setAddress(dto.getAddress());
    setGstNo(dto.getGstNo());
    setCustomerCode(dto.getCustomerCode());
    setRemark(dto.getRemark());
    setHardwareDetail(
        CollectionUtils.emptyIfNull(dto.getHardwareDetail())
        .stream().map(HardwareDetail::new).collect(Collectors.toSet()));
  }

}
