package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.tenant.Customer;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {

  private Long id;
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
  private Boolean active;
  private List<HardwareDetailDTO> hardwareDetail;
  private BillingDetailDTO billingDetail;
  private Set<SubscriptionDTO> subscriptions;
  private Long userId;
  public CustomerDTO(Customer entity) {
    if (entity == null) {
      return;
    }
    setId(entity.getId());
    setName(entity.getName());
    setBillingName(entity.getBillingName());
    setBillingArea(entity.getBillingArea());
    setPrimaryMobileNo(entity.getPrimaryMobileNo());
    setSecondaryMobileNo(entity.getSecondaryMobileNo());
    setEmail(entity.getEmail());
    setSecurityDeposit(entity.getSecurityDeposit());
    setAddress(entity.getAddress());
    setGstNo(entity.getGstNo());
    setCustomerCode(entity.getCustomerCode());
    setRemark(entity.getRemark());
    setActive(entity.getActive());
    setHardwareDetail(
        CollectionUtils.emptyIfNull(entity.getHardwareDetail()).stream().map(HardwareDetailDTO::new)
            .collect(Collectors.toList()));
    setBillingDetail(new BillingDetailDTO(entity.getBillingDetail()));
    setSubscriptions(
        entity.getSubscriptions().stream().map(SubscriptionDTO::new).collect(Collectors.toSet()));
    setUserId(entity.getUserId());
  }

}
