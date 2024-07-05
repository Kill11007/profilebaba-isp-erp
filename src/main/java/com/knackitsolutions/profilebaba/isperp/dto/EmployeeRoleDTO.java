package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.tenant.EmployeeRole;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRoleDTO {

  private Integer id;
  private String roleName;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;
  private List<Long> permissionsId;
  private Set<UserTypeRolePermissionDTO> userTypeRolePermissions = new HashSet<>();

  public EmployeeRoleDTO(EmployeeRole employeeRole) {
    if (employeeRole == null) {
      return;
    }
    setId(employeeRole.getId());
    setRoleName(employeeRole.getRoleName());
    setCreatedDate(employeeRole.getCreatedDate());
    setUpdatedDate(employeeRole.getUpdatedDate());
    setUserTypeRolePermissions(employeeRole.getUserTypeRolePermissions().stream().map(UserTypeRolePermissionDTO::new)
        .collect(Collectors.toSet()));
  }
}
