package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.dto.CustomerRoleDTO;
import com.knackitsolutions.profilebaba.isperp.dto.EmployeeRoleDTO;
import com.knackitsolutions.profilebaba.isperp.service.impl.UserTypeRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user-type/roles")
@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
public class UserTypeRoleController {
    private final UserTypeRoleService userTypeRoleService;
    @GetMapping("/employees")
    public List<EmployeeRoleDTO> getAllEmpRoles() {
        return userTypeRoleService.getEmployeeRoles();
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
    public List<CustomerRoleDTO> getAllCustomerRole() {
        return userTypeRoleService.getCustomerRoles();
    }


}
