package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.dto.ServiceAreaDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.ServiceArea;
import com.knackitsolutions.profilebaba.isperp.exception.ServiceAreaAlreadyExistsException;
import com.knackitsolutions.profilebaba.isperp.exception.ServiceAreaNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.AreaService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("/service-areas")
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class ServiceAreaController {

  private final AreaService areaService;

  @GetMapping("/{id}")
  public ResponseEntity<ServiceAreaDTO> find(@PathVariable Long id) throws ServiceAreaNotFoundException {
    return ResponseEntity.ok(areaService.findById(id));
  }

  @GetMapping
  public ResponseEntity<Page<ServiceAreaDTO>> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                      @RequestParam(required = false, defaultValue = "1000") Integer size) {
    return ResponseEntity.ok(areaService.findAll(page, size));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody @NotNull ServiceAreaDTO dto)
      throws ServiceAreaNotFoundException {
    areaService.update(id, dto);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) throws ServiceAreaNotFoundException {
    areaService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping
  public ResponseEntity<Void> add(@Valid @RequestBody @NotNull ServiceAreaDTO serviceAreaDTO,
      UriComponentsBuilder uriComponentsBuilder) throws ServiceAreaAlreadyExistsException {
    ServiceArea add = areaService.add(serviceAreaDTO);
    return ResponseEntity.created(
            uriComponentsBuilder.path("/service-areas/{id}").buildAndExpand(add.getId()).toUri())
        .build();
  }

}

