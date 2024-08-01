package com.knackitsolutions.profilebaba.isperp.entity.tenant;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.knackitsolutions.profilebaba.isperp.dto.EmployeeRoleDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employee_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRole {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String roleName;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;
  @OneToMany(mappedBy = "employeeRole", fetch = FetchType.EAGER)
  private Set<UserTypeRolePermission> userTypeRolePermissions = new HashSet<>();

  @OneToMany(mappedBy = "employeeRole")
  private Set<Employee> employees = new HashSet<>();

  public EmployeeRole(EmployeeRoleDTO dto) {
    this.setRoleName(dto.getRoleName());
    this.setCreatedDate(LocalDateTime.now());
    this.setUpdatedDate(LocalDateTime.now());
  }
}
