package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.dto.PermissionDTO;
import com.knackitsolutions.profilebaba.isperp.entity.Permission;
import com.knackitsolutions.profilebaba.isperp.exception.PermissionAlreadyExistsException;
import com.knackitsolutions.profilebaba.isperp.exception.PermissionNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.PermissionService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionController {

  private final PermissionService permissionService;

  @GetMapping
  public ResponseEntity<List<PermissionDTO>> getAll() {
    return ResponseEntity.ok(permissionService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<PermissionDTO> getOne(@PathVariable Long id) throws PermissionNotFoundException {
    return ResponseEntity.ok(permissionService.findById(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<PermissionDTO> delete(@PathVariable Long id) throws PermissionNotFoundException {
    permissionService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping
  public ResponseEntity<Void> add(@Valid @RequestBody @NotNull PermissionDTO dto,
      UriComponentsBuilder uriComponentsBuilder) throws PermissionAlreadyExistsException {
    Permission add = permissionService.add(dto);
    return ResponseEntity.created(
            uriComponentsBuilder.path("/permissions/{id}").buildAndExpand(add.getId()).toUri())
        .build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody @NotNull PermissionDTO dto)
      throws PermissionNotFoundException {
    permissionService.update(id, dto);
    return ResponseEntity.noContent().build();
  }

}
