package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.dto.PermissionDTO;
import com.knackitsolutions.profilebaba.isperp.entity.main.Permission;
import com.knackitsolutions.profilebaba.isperp.exception.PermissionAlreadyExistsException;
import com.knackitsolutions.profilebaba.isperp.exception.PermissionNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.PermissionService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@CrossOrigin
public class PermissionController {

  private final PermissionService permissionService;

  @GetMapping
  public ResponseEntity<Page<PermissionDTO>> getAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                    @RequestParam(required = false, defaultValue = "1000") Integer size) {
    return ResponseEntity.ok(permissionService.findAll(page, size));
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

  @PostMapping("/list")
  public ResponseEntity<Void> add(@Valid @RequestBody List<PermissionDTO> dtos) throws PermissionAlreadyExistsException{
    permissionService.add(dtos);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody @NotNull PermissionDTO dto)
      throws PermissionNotFoundException {
    permissionService.update(id, dto);
    return ResponseEntity.noContent().build();
  }

}
