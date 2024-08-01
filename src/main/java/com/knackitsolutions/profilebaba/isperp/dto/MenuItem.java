package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.main.Permission;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.Data;

@Data
public class MenuItem {
  private String menuName;
  private String url;
  private String icon;
  private List<MenuItem> menuItems;

  public MenuItem(List<PermissionDTO> permissionDTOS) {
    List<PermissionDTO> parents = permissionDTOS.stream()
        .filter(dto -> dto.getParentId() == 0)
        .toList();
    List<MenuItem> menus = new ArrayList<>();
    for (PermissionDTO parent : parents) {
      List<MenuItem> items = permissionDTOS.stream()
          .filter(dto -> Objects.equals(dto.getParentId(), parent.getId()))
          .map(MenuItem::new).toList();
      MenuItem item = new MenuItem(parent, items);
      menus.add(item);
    }
    this.menuName = "MENU";
    this.menuItems = menus;
  }

  public static List<MenuItem> createMenu(Set<Permission> permissions) {
    List<Permission> parents = new ArrayList<>(permissions.stream()
            .filter(entity -> entity.getParent().getId() == 0)
            .toList());
    if (parents.isEmpty()) {
      for (Permission permission : permissions) {
        parents.add(getParentPermission(permission));
      }
    }
    List<MenuItem> menus = new ArrayList<>();
    for (Permission parent : parents) {
      List<MenuItem> items = permissions.stream()
              .filter(dto -> dto.getParent() != null)
              .filter(dto -> Objects.equals(dto.getParent().getId(), parent.getId()))
              .map(MenuItem::new).toList();
      if (items.isEmpty()) {
        items = getMenuItems(parent);
      }
      MenuItem item = new MenuItem(parent, items);
      menus.add(item);
    }
    return menus;
  }

  private static List<MenuItem> getMenuItems(Permission parent) {
    Set<Permission> permissions = parent.getPermissions();
    return permissions.stream().map(MenuItem::new).toList();
  }

  private static Permission getParentPermission(Permission permission) {
    if (permission.getParent().getId() == 0) {
      return permission;
    }
    return getParentPermission(permission.getParent());
  }

  public MenuItem(Permission permission) {
    this.menuName = permission.getFeatureName();
    this.url = permission.getUrl();
    this.icon = permission.getIcon();
  }
  public MenuItem(PermissionDTO permission) {
    this.menuName = permission.getFeatureName();
    this.url = permission.getUrl();
    this.icon = permission.getIcon();
  }

  public MenuItem(Permission permission, List<MenuItem> menuItems) {
    this(permission);
    this.menuItems = menuItems;
  }
  public MenuItem(PermissionDTO permission, List<MenuItem> menuItems) {
    this(permission);
    this.menuItems = menuItems;
  }

}
