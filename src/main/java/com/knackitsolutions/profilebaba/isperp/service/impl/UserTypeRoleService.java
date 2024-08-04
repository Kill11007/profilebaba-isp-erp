package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.dto.CustomerRoleDTO;
import com.knackitsolutions.profilebaba.isperp.dto.EmployeeRoleDTO;
import com.knackitsolutions.profilebaba.isperp.dto.PermissionDTO;
import com.knackitsolutions.profilebaba.isperp.dto.UserTypeRolePermissionDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Customer;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.CustomerRole;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.EmployeeRole;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.UserTypeRolePermission;
import com.knackitsolutions.profilebaba.isperp.repository.main.PermissionRepository;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserTypeRoleService {

    private static final RuntimeException CUSTOMER_ROLE_NOT_FOUND = new RuntimeException("CustomerRole not found");
    private static final RuntimeException EMPLOYEE_ROLE_NOT_FOUND = new RuntimeException("EmployeeRole not found");


    private final PermissionRepository permissionRepository;
    private final UserTypeRolePermissionRepository userTypeRolePermissionRepository;
    private final EmployeeRoleRepository employeeRoleRepository;
    private final CustomerRoleRepository customerRoleRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

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
            List<PermissionDTO> permissionDTOS = getPermissionDTOS(dto.getUserTypeRolePermissions());
            dto.setPermissions(permissionDTOS);
        }
        return customerRoleDTOS;
    }

    private List<PermissionDTO> getPermissionDTOS(Set<UserTypeRolePermissionDTO> userTypeRolePermissions) {
        List<PermissionDTO> permissionDTOS = new ArrayList<>();
        for (UserTypeRolePermissionDTO dto1 : userTypeRolePermissions) {
            permissionRepository
                    .findById(dto1.getPermissionId())
                    .ifPresent(permission -> permissionDTOS.add(new PermissionDTO(permission)));
        }
        return permissionDTOS;
    }

    public void deleteCustomerRole(Integer roleId) {
        List<Customer> byCustomerRoleId = customerRepository.findByCustomerRoleId(roleId);
        byCustomerRoleId.stream().peek(customer -> customer.setCustomerRole(null)).forEach(customerRepository::save);
        customerRoleRepository.findById(roleId).ifPresent(customerRole -> {
            customerRole.getUserTypeRolePermissions().forEach(userTypeRolePermissionRepository::delete);
            customerRoleRepository.delete(customerRole);
        });
    }

    public CustomerRoleDTO updateCustomerRole(Integer roleId, CustomerRoleDTO dto) {
        Optional<CustomerRole> byId = customerRoleRepository.findById(roleId);
        if (byId.isPresent()) {
            CustomerRole customerRole = updateCustomerRole(byId.get(), dto);
            CustomerRoleDTO customerRoleDTO = new CustomerRoleDTO(customerRole);
            customerRoleDTO.setPermissions(getPermissionDTOS(customerRoleDTO.getUserTypeRolePermissions()));
            return customerRoleDTO;
        }
        throw CUSTOMER_ROLE_NOT_FOUND;
    }


    private CustomerRole updateCustomerRole(CustomerRole customerRole, CustomerRoleDTO dto) {
        customerRole.update(dto);
        deleteAllPermissions(customerRole.getUserTypeRolePermissions());
        Set<UserTypeRolePermission> userTypeRolePermissions = dto.getPermissionsId()
                .stream()
                .map(permissionId -> new UserTypeRolePermission(customerRole, permissionId))
                .peek(userTypeRolePermissionRepository::save).collect(Collectors.toSet());
        customerRole.setUserTypeRolePermissions(userTypeRolePermissions);
        return customerRoleRepository.save(customerRole);
    }

    private void deleteAllPermissions(Set<UserTypeRolePermission> userTypeRolePermissions) {
        userTypeRolePermissions.forEach(userTypeRolePermissionRepository::delete);
    }

    public void deleteEmployeeRole(Integer roleId) {
        employeeRepository.findByEmployeeRoleId(roleId)
                .stream()
                .peek(employee -> employee.setEmployeeRole(null))
                .forEach(employeeRepository::save);
        employeeRoleRepository.findById(roleId).ifPresent(employeeRole -> {
            deleteAllPermissions(employeeRole.getUserTypeRolePermissions());
            employeeRoleRepository.delete(employeeRole);
        });
    }

    public EmployeeRoleDTO updateEmployeeRole(Integer roleId, EmployeeRoleDTO dto) {
        Optional<EmployeeRole> byId = employeeRoleRepository.findById(roleId);
        if (byId.isPresent()) {
            EmployeeRole employeeRole = updateEmployeeRole(byId.get(), dto);
            EmployeeRoleDTO employeeRoleDTO = new EmployeeRoleDTO(employeeRole);
            employeeRoleDTO.setPermissions(getPermissionDTOS(employeeRoleDTO.getUserTypeRolePermissions()));
            return employeeRoleDTO;
        }
        throw new RuntimeException("EmployeeRole not found");
    }

    private EmployeeRole updateEmployeeRole(EmployeeRole employeeRole, EmployeeRoleDTO dto) {
        employeeRole.update(dto);
        deleteAllPermissions(employeeRole.getUserTypeRolePermissions());
        Set<UserTypeRolePermission> userTypeRolePermissions = dto.getPermissionsId()
                .stream()
                .map(permissionId -> new UserTypeRolePermission(employeeRole, permissionId))
                .peek(userTypeRolePermissionRepository::save)
                .collect(Collectors.toSet());
        employeeRole.setUserTypeRolePermissions(userTypeRolePermissions);
        return employeeRoleRepository.save(employeeRole);
    }

    public EmployeeRoleDTO getEmployeeRole(Integer roleId) {
        EmployeeRole employeeRole = employeeRoleRepository.findById(roleId).orElseThrow(() -> EMPLOYEE_ROLE_NOT_FOUND);
        EmployeeRoleDTO employeeRoleDTO = new EmployeeRoleDTO(employeeRole);
        employeeRoleDTO.setPermissions(getPermissionDTOS(employeeRoleDTO.getUserTypeRolePermissions()));
        return employeeRoleDTO;
    }

    public CustomerRoleDTO getCustomerRole(Integer roleId) {
        CustomerRole customerRole = customerRoleRepository.findById(roleId).orElseThrow(() -> CUSTOMER_ROLE_NOT_FOUND);
        CustomerRoleDTO customerRoleDTO = new CustomerRoleDTO(customerRole);
        customerRoleDTO.setPermissions(getPermissionDTOS(customerRoleDTO.getUserTypeRolePermissions()));
        return customerRoleDTO;
    }
}
