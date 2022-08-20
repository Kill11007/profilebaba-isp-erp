package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.dto.EmployeeDTO;
import com.knackitsolutions.profilebaba.isperp.dto.NewEmployeeRequest;
import com.knackitsolutions.profilebaba.isperp.entity.Employee;
import com.knackitsolutions.profilebaba.isperp.exception.EmployeeAlreadyExistsException;
import com.knackitsolutions.profilebaba.isperp.exception.EmployeeNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.EmployeeService;
import java.util.List;
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
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

  private final EmployeeService employeeService;

  @GetMapping
  public ResponseEntity<List<EmployeeDTO>> all() {
    return ResponseEntity.ok(employeeService.all());
  }

  @GetMapping("/{id}")
  public ResponseEntity<EmployeeDTO> one(@PathVariable Long id) throws EmployeeNotFoundException {
    return ResponseEntity.ok(employeeService.one(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) throws EmployeeNotFoundException {
    employeeService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO dto)
      throws EmployeeNotFoundException {
    employeeService.update(id, dto);
    return ResponseEntity.noContent().build();
  }

  @PostMapping
  public ResponseEntity<Void> newEmployee(@RequestBody NewEmployeeRequest newEmployeeRequest,
      UriComponentsBuilder uriComponentsBuilder)
      throws EmployeeAlreadyExistsException {
    Employee add = employeeService.add(newEmployeeRequest);
    return ResponseEntity.created(
        uriComponentsBuilder.path("/employees/{id}").buildAndExpand(add.getId()).toUri()).build();
  }
}
