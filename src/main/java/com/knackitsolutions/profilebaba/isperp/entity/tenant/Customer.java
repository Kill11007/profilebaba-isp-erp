package com.knackitsolutions.profilebaba.isperp.entity.tenant;

import com.knackitsolutions.profilebaba.isperp.dto.CustomerDTO;
import com.knackitsolutions.profilebaba.isperp.dto.UserCommonInfo;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Customer implements UserCommonInfo {

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

  @OneToMany(mappedBy = "customer")
  private Set<Bill> bills;

  @OneToMany(mappedBy = "customer")
  private Set<AdjustedBalance> adjustedBalances;

  @OneToMany(mappedBy = "customer")
  private List<Payment> payments;

  @OneToMany(mappedBy = "customer")
  private List<BalanceSheet> balanceSheets;

  @OneToMany(mappedBy = "customer")
  private List<Complaint> complaints;

  private Long userId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "role_id", referencedColumnName = "id")
  private CustomerRole customerRole;

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
    setUserId(dto.getUserId());
  }

  @Override
  public String getUserName() {
    return this.name;
  }
}
