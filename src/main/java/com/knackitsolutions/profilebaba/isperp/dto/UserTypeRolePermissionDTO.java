package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.tenant.UserTypeRolePermission;
import com.knackitsolutions.profilebaba.isperp.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTypeRolePermissionDTO {

  private Long id;
  private UserType userType;
  private Long permissionId;
  private Integer customerRoleId;
  private Integer employeeRoleId;

  public UserTypeRolePermissionDTO(UserTypeRolePermission rolePermission) {
    this.id = rolePermission.getId();
    this.userType = rolePermission.getUserType();
    this.permissionId = rolePermission.getPermissionId();
    this.customerRoleId =
        rolePermission.getCustomerRole() != null ? rolePermission.getCustomerRole().getId() : null;
    this.employeeRoleId =
        rolePermission.getEmployeeRole() != null ? rolePermission.getEmployeeRole().getId() : null;
  }

}
