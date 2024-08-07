package com.knackitsolutions.profilebaba.isperp.entity.tenant;

import com.knackitsolutions.profilebaba.isperp.enums.UserType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_type_role_permissions")
@Getter
@Setter
@NoArgsConstructor
public class UserTypeRolePermission {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private UserType userType;

  @ManyToOne
  @JoinColumn(name = "customer_role_id")
  private CustomerRole customerRole;
  @ManyToOne
  @JoinColumn(name = "employee_role_id")
  private EmployeeRole employeeRole;
  private Long permissionId;

  public UserTypeRolePermission(EmployeeRole employeeRole, Long permissionId) {
    this(UserType.EMPLOYEE, employeeRole, permissionId);
  }

  public UserTypeRolePermission(CustomerRole customerRole, Long permissionId) {
    this(UserType.CUSTOMER, customerRole, permissionId);

  }

  public UserTypeRolePermission(UserType userType, EmployeeRole employeeRole, Long permissionId) {
    this.userType = userType;
    this.employeeRole = employeeRole;
    this.permissionId = permissionId;
  }

  public UserTypeRolePermission(UserType userType, CustomerRole customerRole, Long permissionId) {
    this.userType = userType;
    this.customerRole = customerRole;
    this.permissionId = permissionId;
  }
}
