package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.main.User;
import com.knackitsolutions.profilebaba.isperp.enums.UserType;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfo {
  private Long userId;
  private String tenantId;
  private String phoneNumber;
  private UserType userType;
  private Set<PermissionDTO> permissions;
  private boolean enabled;

  public UserInfo(User user) {
    this.setUserId(user.getId());
    this.setTenantId(user.getTenantId());
    this.setEnabled(user.isEnabled());
    this.setPhoneNumber(user.getPhoneNumber());
    this.setUserType(user.getUserType());
    this.setPermissions(
        user.getPermissions().stream().map(PermissionDTO::new).collect(Collectors.toSet()));
  }
}
