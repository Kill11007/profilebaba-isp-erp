package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.dto.CustomerRoleDTO;
import com.knackitsolutions.profilebaba.isperp.dto.EmployeeRoleDTO;
import com.knackitsolutions.profilebaba.isperp.service.impl.UserTypeRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user-type/roles")
@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
public class UserTypeRoleController {
    private final UserTypeRoleService userTypeRoleService;

    @GetMapping("/employees")
    public Page<EmployeeRoleDTO> getAllEmpRoles() {
        return new PageImpl<>(userTypeRoleService.getEmployeeRoles());
    }

    @PostMapping("/employees")
    public ResponseEntity<?> addEmpRole(@RequestBody EmployeeRoleDTO dto) {
        userTypeRoleService.addEmployeeRole(dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/customers")
    public ResponseEntity<?> addCustomerRole(@RequestBody CustomerRoleDTO dto) {
        userTypeRoleService.addCustomerRole(dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customers")
    public Page<CustomerRoleDTO> getAllCustomerRole() {
        return new PageImpl<>(userTypeRoleService.getCustomerRoles());
    }

    @DeleteMapping("/customers/{role-id}")
    public ResponseEntity<?> deleteCustomerRole(@PathVariable("role-id") Integer roleId) {
        userTypeRoleService.deleteCustomerRole(roleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customers/{role-id}")
    public CustomerRoleDTO getCustomerRole(@PathVariable("role-id") Integer roleId) {
        return userTypeRoleService.getCustomerRole(roleId);
    }

    @PutMapping("/customers/{role-id}")
    public CustomerRoleDTO updateCustomerRole(@PathVariable("role-id") Integer roleId, @RequestBody CustomerRoleDTO dto) {
        return userTypeRoleService.updateCustomerRole(roleId, dto);
    }

    @DeleteMapping("/employees/{role-id}")
    public ResponseEntity<?> deleteEmployeeRole(@PathVariable("role-id") Integer roleId) {
        userTypeRoleService.deleteEmployeeRole(roleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employees/{role-id}")
    public EmployeeRoleDTO getEmployeeRole(@PathVariable("role-id") Integer roleId) {
        return userTypeRoleService.getEmployeeRole(roleId);
    }

    @PutMapping("/employees/{role-id}")
    public EmployeeRoleDTO updateEmployeeRole(@PathVariable("role-id") Integer roleId, @RequestBody EmployeeRoleDTO dto) {
        return userTypeRoleService.updateEmployeeRole(roleId, dto);
    }


}
