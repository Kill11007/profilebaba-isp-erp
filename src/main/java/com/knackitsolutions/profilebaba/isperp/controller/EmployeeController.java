package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.controller.VendorController.LoginRequest;
import com.knackitsolutions.profilebaba.isperp.dto.EmployeeDTO;
import com.knackitsolutions.profilebaba.isperp.dto.NewEmployeeRequest;
import com.knackitsolutions.profilebaba.isperp.dto.PermissionDTO;
import com.knackitsolutions.profilebaba.isperp.dto.ServiceAreaDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Employee;
import com.knackitsolutions.profilebaba.isperp.exception.EmployeeAlreadyExistsException;
import com.knackitsolutions.profilebaba.isperp.exception.EmployeeNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.PermissionNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.ServiceAreaNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.EmployeeService;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/employees")
@CrossOrigin
@RequiredArgsConstructor
public class EmployeeController {

  private Supplier<ResponseEntity<Void>> noContent = () -> ResponseEntity.noContent().build();
  private BiFunction<UriComponentsBuilder, Long, ResponseEntity<Void>> created =
      (uriComponentsBuilder, id) -> ResponseEntity.created(
          uriComponentsBuilder.path("/employees/{id}").buildAndExpand(id).toUri()).build();
  private final EmployeeService employeeService;

  @GetMapping
  public ResponseEntity<List<EmployeeDTO>> all() throws UserNotFoundException {
    return ResponseEntity.ok(employeeService.all());
  }

  @GetMapping("/{id}")
  public ResponseEntity<EmployeeDTO> one(@PathVariable Long id)
      throws EmployeeNotFoundException, UserNotFoundException {
    return ResponseEntity.ok(employeeService.one(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) throws EmployeeNotFoundException {
    employeeService.delete(id);
    return noContent.get();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO dto)
      throws EmployeeNotFoundException {
    employeeService.update(id, dto);
    return noContent.get();
  }

  @PostMapping
  public ResponseEntity<Void> newEmployee(@RequestBody NewEmployeeRequest newEmployeeRequest,
      UriComponentsBuilder uriComponentsBuilder)
      throws EmployeeAlreadyExistsException, PermissionNotFoundException, ServiceAreaNotFoundException {
    Employee add = employeeService.add(newEmployeeRequest);
    return created.apply(uriComponentsBuilder, add.getId());
  }

  @DeleteMapping("/{employee-id}/service-area/{area-id}")
  public ResponseEntity<Void> removeServiceArea(@PathVariable("employee-id") Long employeeId,
      @PathVariable("area-id") Long areaId)
      throws ServiceAreaNotFoundException, EmployeeNotFoundException {
    employeeService.removeServiceArea(employeeId, areaId);
    return noContent.get();
  }

  @DeleteMapping("/{employee-id}/permission/{permission-id}")
  public ResponseEntity<Void> removePermission(@PathVariable("employee-id") Long employeeId,
      @PathVariable("permission-id") Long permissionId)
      throws EmployeeNotFoundException, PermissionNotFoundException, UserNotFoundException {
    employeeService.removePermission(employeeId, permissionId);
    return noContent.get();
  }

  @PostMapping("/{employee-id}/service-areas")
  public ResponseEntity<Void> addServiceAreas(@PathVariable("employee-id") Long employeeId,
      @RequestBody List<ServiceAreaDTO> serviceAreaDTOS, UriComponentsBuilder uriComponentsBuilder)
      throws ServiceAreaNotFoundException, EmployeeNotFoundException {
    employeeService.addServiceAreas(employeeId, serviceAreaDTOS);
    return created.apply(uriComponentsBuilder, employeeId);
  }

  @PostMapping("/{employee-id}/permissions")
  public ResponseEntity<Void> addPermissions(@PathVariable("employee-id") Long employeeId,
      @RequestBody List<PermissionDTO> permissionDTOS, UriComponentsBuilder uriComponentsBuilder)
      throws PermissionNotFoundException, EmployeeNotFoundException, UserNotFoundException {
    employeeService.addPermissions(employeeId, permissionDTOS);
    return created.apply(uriComponentsBuilder, employeeId);
  }

  @PostMapping("/login")
  public ResponseEntity<EmployeeDTO> login(@RequestBody @Valid LoginRequest loginRequest) {
    return null;
  }
}
