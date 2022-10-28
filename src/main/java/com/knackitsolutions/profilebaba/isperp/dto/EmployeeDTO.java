package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.main.User;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Employee;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
  private Long id;
  private String name;
  private String address;
  private String email;
  private String phone;
  private Set<ServiceAreaDTO> areas;
  private Set<PermissionDTO> permissions;

  public EmployeeDTO(Employee entity, User user) {
    setId(entity.getId());
    setName(entity.getName());
    setEmail(entity.getEmail());
    setPhone(user.getPhoneNumber());
    setAddress(entity.getAddress());
    setAreas(
        entity.getServiceAreas().stream().map(ServiceAreaDTO::new).collect(Collectors.toSet()));

    setPermissions(
        user.getPermissions().stream().map(PermissionDTO::new).collect(Collectors.toSet()));
  }

  public EmployeeDTO(Employee employee) {
    this.setId(employee.getId());
    this.setName(employee.getName());
    this.setAddress(employee.getAddress());
    this.setEmail(employee.getEmail());
    this.setAreas(employee.getServiceAreas().stream().map(ServiceAreaDTO::new).collect(
        Collectors.toSet()));
    this.setPhone(employee.getPhone());
  }
}
