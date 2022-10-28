package com.knackitsolutions.profilebaba.isperp.service;

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
import java.util.List;

public interface EmployeeService {

  Employee add(NewEmployeeRequest request)
      throws EmployeeAlreadyExistsException, PermissionNotFoundException, ServiceAreaNotFoundException;

  void update(Long id, EmployeeDTO dto) throws EmployeeNotFoundException, UserNotFoundException;

  EmployeeDTO one(Long id) throws EmployeeNotFoundException, UserNotFoundException;

  List<EmployeeDTO> all() throws UserNotFoundException;

  void delete(Long id) throws EmployeeNotFoundException, UserNotFoundException;


  Employee save(Employee employee);

  Employee findById(Long id) throws EmployeeNotFoundException, UserNotFoundException;

  void removeServiceArea(Long employeeId, Long areaId)
      throws EmployeeNotFoundException, ServiceAreaNotFoundException, UserNotFoundException;

  void addServiceAreas(Long employeeId, List<ServiceAreaDTO> serviceAreaDTOS)
      throws EmployeeNotFoundException, ServiceAreaNotFoundException, UserNotFoundException;

  void addPermissions(Long employeeId, List<PermissionDTO> permissionDTOS)
      throws EmployeeNotFoundException, PermissionNotFoundException, UserNotFoundException;

  void removePermission(Long employeeId, Long permissionId)
      throws EmployeeNotFoundException, PermissionNotFoundException, UserNotFoundException;

  EmployeeDTO login(LoginRequest loginRequest) throws EmployeeNotFoundException;
}
