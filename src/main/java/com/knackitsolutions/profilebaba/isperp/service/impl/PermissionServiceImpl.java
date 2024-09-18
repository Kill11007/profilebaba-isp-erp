package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.dto.PermissionDTO;
import com.knackitsolutions.profilebaba.isperp.entity.main.Permission;
import com.knackitsolutions.profilebaba.isperp.exception.PermissionAlreadyExistsException;
import com.knackitsolutions.profilebaba.isperp.exception.PermissionNotFoundException;
import com.knackitsolutions.profilebaba.isperp.repository.main.PermissionRepository;
import com.knackitsolutions.profilebaba.isperp.service.PermissionService;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
  private final PermissionRepository repository;

  @Override
  public Permission add(PermissionDTO dto) throws PermissionAlreadyExistsException {
    if (repository.existsByName(dto.getName())) {
      throw new PermissionAlreadyExistsException();
    }
    Permission permission = new Permission(dto);
    repository.findById(dto.getId()).ifPresent(permission::setParent);
    return save(permission);
  }

  @Override
  public List<Permission> add(List<PermissionDTO> dtos) throws PermissionAlreadyExistsException {
      List<Permission> list = new ArrayList<>();
      for (PermissionDTO dto : dtos) {
          Permission add = add(dto);
          list.add(add);
      }
      return list;
  }

  @Override
  public Permission save(Permission permission) {
    return repository.save(permission);
  }

  @Override
  public PermissionDTO findById(Long id) throws PermissionNotFoundException {
    return new PermissionDTO(get(id));
  }

  @Override
  public Permission get(Long id) throws PermissionNotFoundException {
    return repository.findById(id).orElseThrow(PermissionNotFoundException::new);
  }

  @Override
  public Page<PermissionDTO> findAll(Integer page, Integer size) {
    Page<Permission> all = repository.findAll(PageRequest.of(page, size));
    return new PageImpl<>(getPermissionDTOs(all));
  }

  private List<PermissionDTO> getPermissionDTOs(Page<Permission> all) {
    List<Permission> parentPermission = all.stream()
            .filter(permission -> permission.getId() != 0)
            .filter(permission -> permission.getParent() != null)
            .filter(permission -> permission.getParent().getId() == 0)
            .toList();
    return parentPermission.stream().map(PermissionDTO::new).toList();
  }

  /*public Set<Permission> getParentToChildPermissionDTOSet(List<Permission> permissions){
    for (Permission permission : permissions) {
      if (permission.getParent() != null) {

      }
    }
  }*/

  @Override
  public void delete(Long id) throws PermissionNotFoundException {
    Permission permission = get(id);
    delete(permission);
  }

  @Override
  public void delete(Permission permission) {
    repository.delete(permission);
  }

  @Override
  public void update(Long id, PermissionDTO dto) throws PermissionNotFoundException {
    Permission permission = get(id);
    permission.update(dto);
    repository.save(permission);
  }
}