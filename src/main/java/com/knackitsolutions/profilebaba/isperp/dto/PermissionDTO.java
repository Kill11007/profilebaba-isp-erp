package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.Permission;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDTO {
  private Long id;
  @NotNull
  private String name;

  public PermissionDTO(Permission permission) {
    if (permission == null) {
      return;
    }
    this.id = permission.getId();
    this.name = permission.getName();
  }
}
