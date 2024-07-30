package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.main.Permission;
import java.util.ArrayList;
import java.util.List;
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
          .filter(dto -> dto.getParentId() == parent.getId())
          .map(MenuItem::new).toList();
      MenuItem item = new MenuItem(parent, items);
      menus.add(item);
    }
    this.menuName = "MENU";
    this.menuItems = menus;
  }

  public static List<MenuItem> createMenu(Set<Permission> permissions) {
    List<Permission> parents = permissions.stream()
        .filter(dto -> dto.getParent().getId() == 0)
        .toList();
    List<MenuItem> menus = new ArrayList<>();
    for (Permission parent : parents) {
      List<MenuItem> items = permissions.stream()
          .filter(dto -> dto.getParent() != null)
          .filter(dto -> dto.getParent().getId() == parent.getId())
          .map(MenuItem::new).toList();
      MenuItem item = new MenuItem(parent, items);
      menus.add(item);
    }
    return menus;
  }

  public MenuItem(String menuName) {
    this.menuName = menuName;
  }

  public MenuItem(Permission permission) {
    this.menuName = permission.getFeatureName();
    this.url = permission.getUrl();
    this.icon = permission.getIcons();
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