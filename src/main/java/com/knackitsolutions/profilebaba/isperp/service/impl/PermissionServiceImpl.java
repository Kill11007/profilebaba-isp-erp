package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.dto.PermissionDTO;
import com.knackitsolutions.profilebaba.isperp.entity.Permission;
import com.knackitsolutions.profilebaba.isperp.exception.PermissionAlreadyExistsException;
import com.knackitsolutions.profilebaba.isperp.exception.PermissionNotFoundException;
import com.knackitsolutions.profilebaba.isperp.repository.PermissionRepository;
import com.knackitsolutions.profilebaba.isperp.service.PermissionService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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
    return save(new Permission(dto));
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
  public List<PermissionDTO> findAll() {
    return repository.findAll().stream().map(PermissionDTO::new).collect(Collectors.toList());
  }

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