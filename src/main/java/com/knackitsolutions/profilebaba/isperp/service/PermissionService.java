package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.dto.PermissionDTO;
import com.knackitsolutions.profilebaba.isperp.entity.Permission;
import com.knackitsolutions.profilebaba.isperp.exception.PermissionAlreadyExistsException;
import com.knackitsolutions.profilebaba.isperp.exception.PermissionNotFoundException;
import java.util.List;

public interface PermissionService {
  Permission add(PermissionDTO dto) throws PermissionAlreadyExistsException;
  Permission save(Permission permission);
  PermissionDTO findById(Long id) throws PermissionNotFoundException;
  Permission get(Long id) throws PermissionNotFoundException;
  List<PermissionDTO> findAll();
  void delete(Long id) throws PermissionNotFoundException;
  void delete(Permission permission);
  void update(Long id, PermissionDTO dto) throws PermissionNotFoundException;
}
