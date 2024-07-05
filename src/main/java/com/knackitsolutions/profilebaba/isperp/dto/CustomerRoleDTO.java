package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.tenant.CustomerRole;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRoleDTO {

  private Integer id;
  private String roleName;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;
  private List<Long> permissionsId;
  private Set<UserTypeRolePermissionDTO> userTypeRolePermissions = new HashSet<>();

  public CustomerRoleDTO(CustomerRole customerRole) {
    if (customerRole == null) return;
    setId(customerRole.getId());
    setRoleName(customerRole.getRoleName());
    setCreatedDate(customerRole.getCreatedDate());
    setUpdatedDate(customerRole.getUpdatedDate());
    this.userTypeRolePermissions = customerRole.getUserTypeRolePermissions().stream()
        .map(UserTypeRolePermissionDTO::new).collect(
            Collectors.toSet());
  }

}
