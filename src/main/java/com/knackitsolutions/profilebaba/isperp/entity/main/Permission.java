package com.knackitsolutions.profilebaba.isperp.entity.main;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.knackitsolutions.profilebaba.isperp.dto.PermissionDTO;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
  @Exclude
  private Set<User> users;

  private String featureName;
  @ManyToOne
  @JoinColumn(name="parent_id")
  @JsonBackReference
  private Permission parent;

  @OneToMany(mappedBy = "parent")
  private Set<Permission> permissions = new HashSet<>();

  @OneToMany(mappedBy = "permission")
  Set<IspPlanPermission> ispPlanPermissions = new HashSet<>();

  @OneToMany(mappedBy = "permission")
  Set<UserRoleFeature> userRoleFeatures = new HashSet<>();

  public Permission(PermissionDTO dto) {
    this.name = dto.getName();
  }

  public void update(PermissionDTO dto) {
    this.setName(dto.getName());
  }
}
