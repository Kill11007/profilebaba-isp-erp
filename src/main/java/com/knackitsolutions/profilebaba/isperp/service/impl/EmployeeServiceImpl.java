package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.config.TenantContext;
import com.knackitsolutions.profilebaba.isperp.controller.VendorController.LoginRequest;
import com.knackitsolutions.profilebaba.isperp.dto.EmployeeDTO;
import com.knackitsolutions.profilebaba.isperp.dto.NewEmployeeRequest;
import com.knackitsolutions.profilebaba.isperp.dto.PermissionDTO;
import com.knackitsolutions.profilebaba.isperp.dto.ServiceAreaDTO;
import com.knackitsolutions.profilebaba.isperp.entity.main.User;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Employee;
import com.knackitsolutions.profilebaba.isperp.entity.main.Permission;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.ServiceArea;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.UserTypeRolePermission;
import com.knackitsolutions.profilebaba.isperp.enums.UserType;
import com.knackitsolutions.profilebaba.isperp.exception.EmployeeAlreadyExistsException;
import com.knackitsolutions.profilebaba.isperp.exception.EmployeeNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.PermissionNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.ServiceAreaNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.EmployeeRepository;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.EmployeeRoleRepository;
import com.knackitsolutions.profilebaba.isperp.service.AreaService;
import com.knackitsolutions.profilebaba.isperp.service.EmployeeService;
import com.knackitsolutions.profilebaba.isperp.service.PermissionService;
import com.knackitsolutions.profilebaba.isperp.service.UserService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository repository;
  private final PermissionService permissionService;
  private final AreaService areaService;
  private final PasswordEncoder passwordEncoder;
  private final UserService userService;
  private final EmployeeRoleRepository employeeRoleRepository;

  @Override
  public Employee add(NewEmployeeRequest request)
      throws EmployeeAlreadyExistsException, PermissionNotFoundException, ServiceAreaNotFoundException {
    if (userService.existsByPhoneNumber(request.getPhone())) {
      throw new EmployeeAlreadyExistsException(
          "Phone number already exists. Please provide new phone number.");
    }
    User user = createUser(request);
    Employee employee = new Employee(request);
    if (!request.getAreas().isEmpty()) {
      employee.setServiceAreas(getServiceAreas(request.getAreas()));
    }
    employee.setUserId(user.getId());
    if(request.getEmployeeRoleId() != null) {
      employeeRoleRepository.findById(request.getEmployeeRoleId())
              .ifPresent(employee::setEmployeeRole);
    }
    Employee save = save(employee);
    userService.setUserPermissions(save.getUserId(), save.getEmployeeRole().getUserTypeRolePermissions().stream().map(UserTypeRolePermission::getPermissionId).collect(Collectors.toList()));
    return save;
  }

  private User createUser(NewEmployeeRequest request)
      throws PermissionNotFoundException {
    User user = new User();
    user.setPhoneNumber(request.getPhone());
    user.setTenantId(TenantContext.getTenantId());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setPermissions(getPermissions(request.getPermissions()));
    user.setUserType(UserType.EMPLOYEE);
    return userService.save(user);
  }

  private Set<ServiceArea> getServiceAreas(List<Long> serviceAreaIds)
      throws ServiceAreaNotFoundException {
    Set<ServiceArea> serviceAreas = new HashSet<>();
    for (Long id : CollectionUtils.emptyIfNull(serviceAreaIds)) {
      serviceAreas.add(areaService.get(id));
    }
    return serviceAreas;
  }

  private Set<ServiceArea> serviceAreasByDTOS(List<ServiceAreaDTO> serviceAreaDTOS)
      throws ServiceAreaNotFoundException {
    return getServiceAreas(
        serviceAreaDTOS.stream().map(ServiceAreaDTO::getId).collect(Collectors.toList()));
  }

  private Set<Permission> getPermissions(List<Long> permissionIds) throws PermissionNotFoundException {
    Set<Permission> permissions = new HashSet<>();
    for (Long id : CollectionUtils.emptyIfNull(permissionIds)) {
      permissions.add(permissionService.get(id));
    }
    return permissions;
  }

  private Set<Permission> permissionsByDTOS(List<PermissionDTO> permissionDTOS)
      throws PermissionNotFoundException {
    return getPermissions(
        permissionDTOS.stream().map(PermissionDTO::getId).collect(Collectors.toList()));
  }

  @Override
  public void update(Long id, EmployeeDTO dto)
      throws EmployeeNotFoundException, UserNotFoundException {
    Employee employee = findById(id);
    employee.update(dto);
    employee.setServiceAreas(getServiceAreas(dto.getServiceAreas()));
    if(dto.getEmployeeRoleId() != null) {
      employeeRoleRepository.findById(dto.getEmployeeRoleId())
              .ifPresent(employee::setEmployeeRole);
    }
    Employee save = save(employee);
    List<Long> permissions = save.getEmployeeRole().getUserTypeRolePermissions().stream().map(UserTypeRolePermission::getPermissionId).collect(Collectors.toList());
    userService.setUserPermissions(save.getUserId(), permissions);

  }

  @Override
  public EmployeeDTO one(Long id) throws EmployeeNotFoundException, UserNotFoundException {
    Employee employee = findById(id);
    User user = userService.findById(employee.getUserId());
    EmployeeDTO employeeDTO = new EmployeeDTO(findById(id), user);
    return employeeDTO;
  }

  @Override
  public Page<EmployeeDTO> all(Integer page, Integer size) throws UserNotFoundException {
    return repository.findAll(PageRequest.of(page, size))
            .map(employee -> new EmployeeDTO(employee, userService.findById(employee.getUserId())));
  }

  @Override
  public void delete(Long id) throws EmployeeNotFoundException, UserNotFoundException {
    Employee byId = findById(id);
    userService.deleteByUserId(byId.getUserId());
    repository.delete(byId);
  }

  @Override
  public Employee save(Employee employee) {
    return repository.save(employee);
  }

  @Override
  public Employee findById(Long id) throws EmployeeNotFoundException, UserNotFoundException {
    return repository.findById(id).orElseThrow(EmployeeNotFoundException::new);
  }

  @Override
  public void removeServiceArea(Long employeeId, Long areaId)
      throws EmployeeNotFoundException, ServiceAreaNotFoundException, UserNotFoundException {
    Employee employee = findById(employeeId);
    ServiceArea serviceArea = areaService.get(areaId);
    employee.getServiceAreas().remove(serviceArea);
    repository.save(employee);
  }

  @Override
  public void addServiceAreas(Long employeeId, List<ServiceAreaDTO> serviceAreaDTOS)
      throws EmployeeNotFoundException, ServiceAreaNotFoundException, UserNotFoundException {
    Employee employee = findById(employeeId);
    Set<ServiceArea> serviceAreas = serviceAreasByDTOS(serviceAreaDTOS);
    employee.getServiceAreas().addAll(serviceAreas);
    repository.save(employee);
  }

  @Override
  public void addPermissions(Long employeeId, List<PermissionDTO> permissionDTOS)
      throws PermissionNotFoundException, UserNotFoundException, EmployeeNotFoundException {
    Set<Permission> permissions = permissionsByDTOS(permissionDTOS);
    Employee employee = findById(employeeId);
    User user = userService.findById(employee.getUserId());
    user.getPermissions().addAll(permissions);
    userService.save(user);
  }

  @Override
  public void removePermission(Long employeeId, Long permissionId)
      throws PermissionNotFoundException, UserNotFoundException, EmployeeNotFoundException {
    Employee employee = findById(employeeId);
    User user = userService.findById(employee.getUserId());
    Permission permission = permissionService.get(permissionId);
    user.getPermissions().remove(permission);
    userService.save(user);
  }

  public EmployeeDTO login(LoginRequest loginRequest) {
    return null;
  }

  @Override
  public Employee findByUserId(Long userId) throws EmployeeNotFoundException {
    return repository.findByUserId(userId).orElseThrow(EmployeeNotFoundException::new);
  }
}
