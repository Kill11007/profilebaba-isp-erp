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

import com.knackitsolutions.profilebaba.isperp.dto.CustomerRoleDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRole {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String roleName;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;

  @OneToMany(mappedBy = "customerRole", fetch = FetchType.EAGER)
  private Set<UserTypeRolePermission> userTypeRolePermissions = new HashSet<>();

  @OneToMany(mappedBy = "customerRole")
  private Set<Customer> customers = new HashSet<>();
  public CustomerRole(CustomerRoleDTO dto) {
    this.setRoleName(dto.getRoleName());
    this.setCreatedDate(LocalDateTime.now());
    this.setUpdatedDate(LocalDateTime.now());
  }
}
