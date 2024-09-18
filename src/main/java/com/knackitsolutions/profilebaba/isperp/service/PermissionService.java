package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.dto.PermissionDTO;
import com.knackitsolutions.profilebaba.isperp.entity.main.Permission;
import com.knackitsolutions.profilebaba.isperp.exception.PermissionAlreadyExistsException;
import com.knackitsolutions.profilebaba.isperp.exception.PermissionNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PermissionService {
  Permission add(PermissionDTO dto) throws PermissionAlreadyExistsException;
  List<Permission> add(List<PermissionDTO> dtos) throws PermissionAlreadyExistsException;
  Permission save(Permission permission);
  PermissionDTO findById(Long id) throws PermissionNotFoundException;
  Permission get(Long id) throws PermissionNotFoundException;
  Page<PermissionDTO> findAll(Integer page, Integer size);
  void delete(Long id) throws PermissionNotFoundException;
  void delete(Permission permission);
  void update(Long id, PermissionDTO dto) throws PermissionNotFoundException;
}
