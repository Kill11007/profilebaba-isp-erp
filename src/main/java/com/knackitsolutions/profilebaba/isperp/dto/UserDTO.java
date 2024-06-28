package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.main.User;
import com.knackitsolutions.profilebaba.isperp.enums.UserType;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class UserDTO {
  private Long id;
  private String tenantId;
  private String phoneNumber;
  private String password;
  private Boolean rememberMe = false;
  private Boolean isPhoneNumberVerified = false;
  private UserType userType;
  private Set<PermissionDTO> permissions;
  private boolean enabled;

  public UserDTO(User user) {
    this.setId(user.getId());
    this.setTenantId(user.getTenantId());
    this.setPassword(user.getPassword());
    this.setEnabled(user.isEnabled());
    this.setUserType(user.getUserType());
    this.setPermissions(
        user.getPermissions().stream().map(PermissionDTO::new).collect(Collectors.toSet()));
    this.setRememberMe(user.getRememberMe());
    this.setIsPhoneNumberVerified(user.getIsPhoneNumberVerified());
  }
}
