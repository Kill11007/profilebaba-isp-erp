package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.main.Permission;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDTO {
  private Long id;
  @NotNull
  private String name;

  private String featureName;
  private Long parentId;
  private String url;
  private String icon;
  private List<PermissionDTO> permissions;
  public PermissionDTO(Permission permission) {
    if (permission == null || permission.getId() <= 0) {
      return;
    }
    this.id = permission.getId();
    this.name = permission.getName();
    this.parentId = permission.getParent() != null ? permission.getParent().getId() : null;
    this.featureName = permission.getFeatureName();
    this.url = permission.getUrl();
    this.icon = permission.getIcon();
    Set<Permission> permissionSet = permission.getPermissions();
    this.permissions = permissionSet.stream().map(PermissionDTO::new).collect(Collectors.toList());
  }

  public PermissionDTO(Long id) {
    this.id = id;
  }
}
