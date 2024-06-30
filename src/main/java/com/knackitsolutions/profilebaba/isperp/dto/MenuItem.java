package com.knackitsolutions.profilebaba.isperp.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

  public MenuItem(String menuName) {
    this.menuName = menuName;
  }

  public MenuItem(String menuName, List<MenuItem> menuItems) {
    this(menuName);
    this.menuItems = menuItems;
  }

}
