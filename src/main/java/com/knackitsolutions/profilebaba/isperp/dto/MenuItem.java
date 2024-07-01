package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.main.Permission;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.Data;

@Data
public class MenuItem {
  private String menuName;
  private List<MenuItem> menuItems;

  public MenuItem(List<PermissionDTO> permissionDTOS) {
    List<PermissionDTO> parents = permissionDTOS.stream()
        .filter(dto -> dto.getParentId() == 0)
        .toList();
    List<MenuItem> menus = new ArrayList<>();
    for (PermissionDTO parent : parents) {
      List<MenuItem> items = permissionDTOS.stream()
          .filter(dto -> dto.getParentId() == parent.getId()).map(PermissionDTO::getFeatureName)
          .map(MenuItem::new).toList();
      MenuItem item = new MenuItem(parent.getFeatureName(), items);
      menus.add(item);
    }
    this.menuName = "MENU";
    this.menuItems = menus;
  }

  public static MenuItem createMenu(Set<Permission> permissions) {
    List<Permission> parents = permissions.stream()
        .filter(dto -> dto.getParent().getId() == 0)
        .toList();
    List<MenuItem> menus = new ArrayList<>();
    for (Permission parent : parents) {
      List<MenuItem> items = permissions.stream()
          .filter(dto -> dto.getParent() != null)
          .filter(dto -> dto.getParent().getId() == parent.getId()).map(Permission::getFeatureName)
          .map(MenuItem::new).toList();
      MenuItem item = new MenuItem(parent.getFeatureName(), items);
      menus.add(item);
    }
    return new MenuItem("MENU", menus);
  }

  public MenuItem(String menuName) {
    this.menuName = menuName;
  }

  public MenuItem(String menuName, List<MenuItem> menuItems) {
    this(menuName);
    this.menuItems = menuItems;
  }

}
