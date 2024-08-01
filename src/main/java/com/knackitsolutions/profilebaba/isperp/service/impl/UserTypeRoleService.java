package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.dto.CustomerRoleDTO;
import com.knackitsolutions.profilebaba.isperp.dto.EmployeeRoleDTO;
import com.knackitsolutions.profilebaba.isperp.dto.PermissionDTO;
import com.knackitsolutions.profilebaba.isperp.dto.UserTypeRolePermissionDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.CustomerRole;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.EmployeeRole;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.UserTypeRolePermission;
import com.knackitsolutions.profilebaba.isperp.repository.main.PermissionRepository;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.CustomerRoleRepository;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.EmployeeRoleRepository;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.UserTypeRolePermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserTypeRoleService {
    private final PermissionRepository permissionRepository;
    private final UserTypeRolePermissionRepository userTypeRolePermissionRepository;
    private final EmployeeRoleRepository employeeRoleRepository;
    private final CustomerRoleRepository customerRoleRepository;
    public void addEmployeeRole(EmployeeRoleDTO dto) {
        EmployeeRole employeeRole = new EmployeeRole(dto);
        EmployeeRole saved = employeeRoleRepository.save(employeeRole);
        dto.getPermissionsId()
                .stream()
                .map(permissionId -> new UserTypeRolePermission(saved, permissionId))
                .forEach(userTypeRolePermissionRepository::save);
    }

    public void addCustomerRole(CustomerRoleDTO dto) {
        CustomerRole customerRole = new CustomerRole(dto);
        CustomerRole saved = customerRoleRepository.save(customerRole);
        dto.getPermissionsId()
                .stream()
                .map(permissionId -> new UserTypeRolePermission(saved, permissionId))
                .forEach(userTypeRolePermissionRepository::save);
    }

    public List<EmployeeRoleDTO> getEmployeeRoles() {
        List<EmployeeRoleDTO> list = employeeRoleRepository.findAll().stream().map(EmployeeRoleDTO::new).toList();
        for (EmployeeRoleDTO dto : list) {
            List<PermissionDTO> permissionDTOS = new ArrayList<>();
            for (UserTypeRolePermissionDTO dto1 : dto.getUserTypeRolePermissions()) {
                permissionRepository
                        .findById(dto1.getPermissionId())
                        .ifPresent(permission -> permissionDTOS.add(new PermissionDTO(permission)));
            }
            dto.setPermissions(permissionDTOS);
        }
        return list;
    }

    public List<CustomerRoleDTO> getCustomerRoles() {
        List<CustomerRoleDTO> customerRoleDTOS = customerRoleRepository.findAll().stream().map(CustomerRoleDTO::new).toList();
        for (CustomerRoleDTO dto : customerRoleDTOS) {
            List<PermissionDTO> permissionDTOS = new ArrayList<>();
            Set<UserTypeRolePermissionDTO> userTypeRolePermissions = dto.getUserTypeRolePermissions();
            for (UserTypeRolePermissionDTO dto1 : userTypeRolePermissions) {
                permissionRepository
                        .findById(dto1.getPermissionId())
                        .ifPresent(permission -> permissionDTOS.add(new PermissionDTO(permission)));
            }
            dto.setPermissions(permissionDTOS);
        }
        return customerRoleDTOS;
    }
}
