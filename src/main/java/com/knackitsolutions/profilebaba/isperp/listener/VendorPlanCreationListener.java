package com.knackitsolutions.profilebaba.isperp.listener;

import com.knackitsolutions.profilebaba.isperp.entity.main.*;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.*;
import com.knackitsolutions.profilebaba.isperp.event.DeleteISPEvent;
import com.knackitsolutions.profilebaba.isperp.event.ISPPlanAssignmentEvent;
import com.knackitsolutions.profilebaba.isperp.event.ISPPlanDeactivateEvent;
import com.knackitsolutions.profilebaba.isperp.event.IspPlanChangeEvent;
import com.knackitsolutions.profilebaba.isperp.exception.PlanNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import com.knackitsolutions.profilebaba.isperp.repository.main.TenantRepository;
import com.knackitsolutions.profilebaba.isperp.repository.main.UserRepository;
import com.knackitsolutions.profilebaba.isperp.repository.main.VendorPlanRepository;
import com.knackitsolutions.profilebaba.isperp.repository.main.VendorRepository;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.CustomerRepository;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.EmployeeRepository;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.UserTypeRolePermissionRepository;
import com.knackitsolutions.profilebaba.isperp.service.UserService;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VendorPlanCreationListener {
  private final VendorPlanRepository vendorPlanRepository;
  private final UserService userService;
  private final UserRepository userRepository;
  private final TenantRepository tenantRepository;
  private final EmployeeRepository employeeRepository;
  private final CustomerRepository customerRepository;
  private final UserTypeRolePermissionRepository userTypeRolePermissionRepository;
  private final VendorRepository vendorRepository;
  private final JdbcTemplate jdbcTemplate;

  @EventListener
  public void handleISPDeleteEvent(DeleteISPEvent event) {
    Vendor vendor = event.getVendor();
    User user = userService.findById(vendor.getUserId());
    tenantRepository.findByTenantId(user.getTenantId()).ifPresent(this::deleteAllTenantUser);
    vendorRepository.delete(vendor);
  }

  private void deleteAllTenantUser(Tenant tenant) {
    List<User> users = userRepository.findAllByTenantId(tenant.getTenantId());
    users.forEach(userRepository::delete);
    jdbcTemplate.execute((StatementCallback<Boolean>) stmt ->
            stmt.execute("DROP DATABASE IF EXISTS " + tenant.getDb()));
    jdbcTemplate.execute((StatementCallback<Boolean>) stmt ->
            stmt.execute("DROP USER IF EXISTS '" + tenant.getDb() + "'@'localhost'"));
  }


  @EventListener
  public void handleISPPlanAssignmentEvent(ISPPlanAssignmentEvent event)
      throws UserNotFoundException {
    List<VendorPlan> activePlans = event.getVendor().getVendorPlans().stream()
        .filter(vendorPlan -> vendorPlan.getEndDateTime() == null).toList();
    if (!activePlans.isEmpty()){
      throw new RuntimeException("Plan Already Assigned Plan-Id: " + activePlans.stream().findFirst().get().getPlan().getId());
    }
    VendorPlan vendorPlan = new VendorPlan(event.getVendor(), event.getIspPlan());
    User user = userService.findById(event.getVendor().getUserId());
    Optional<Tenant> byTenantId = tenantRepository.findByTenantId(user.getTenantId());
    byTenantId.ifPresent(vendorPlan::setTenant);
    vendorPlanRepository.save(vendorPlan);
  }

  @EventListener
  public void handleISPPlanDeactivateEvent(ISPPlanDeactivateEvent event) {
    Set<VendorPlan> vendorPlans = event.getVendor().getVendorPlans();
    if (vendorPlans.isEmpty()) {
      throw new PlanNotFoundException();
    }
    VendorPlan vendorPlan = vendorPlans.stream().findFirst()
        .orElseThrow(PlanNotFoundException::new);
    vendorPlan.setEndDateTime(LocalDateTime.now());
    vendorPlan.setUpdatedDateTime(LocalDateTime.now());
    vendorPlanRepository.save(vendorPlan);
    //TODO update user permissions
  }

  @EventListener
  public void handleISPPlanChangeEvent(IspPlanChangeEvent ispPlanChangeEvent) {
    //TODO update isp_plans, user_permissions
    IspPlan plan = ispPlanChangeEvent.getPlan();
    VendorPlan vendorPlan = ispPlanChangeEvent.getVendorPlan();
    vendorPlan.setPlan(plan);
    vendorPlan.setUpdatedDateTime(LocalDateTime.now());
    vendorPlanRepository.save(vendorPlan);
    Vendor vendor = vendorPlan.getVendor();
    User user = userService.findById(vendor.getUserId());
    String tenantId = user.getTenantId();
    List<User> allByTenantId = userRepository.findAllByTenantId(tenantId);
    changeUserPermissions(allByTenantId, plan);

  }

  private void changeUserPermissions(List<User> allByTenantId, IspPlan plan) {
    for (User user : allByTenantId) {
      user.setPermissions(null);
      User save = userRepository.save(user);
      switch (save.getUserType()) {
        case ISP -> updateUserPermissions(plan, user, save);
        case EMPLOYEE -> {
          Optional<Employee> employee = employeeRepository.findByUserId(save.getId());
          employee.ifPresent(emp -> setEmployeePermissions(emp, plan, save));
        }
        case CUSTOMER -> {
          Optional<Customer> customer = customerRepository.findByUserId(save.getId());
          customer.ifPresent(cust -> setCustomerPermissions(cust, plan, save));
        }
      }
    }
  }

  private void updateUserPermissions(IspPlan plan, User user, User save) {
    save.setPermissions(plan.getIspPlanPermissions()
            .stream()
            .map(IspPlanPermission::getPermission)
            .collect(Collectors.toSet()));
    userRepository.save(user);
  }

  private void setCustomerPermissions(Customer customer, IspPlan plan, User user) {
    CustomerRole customerRole = customer.getCustomerRole();
    Set<UserTypeRolePermission> userTypeRolePermissions = customerRole.getUserTypeRolePermissions();
    updateUserTypeRolePermissions(userTypeRolePermissions, plan, user);
  }

  private void setEmployeePermissions(Employee employee, IspPlan ispPlan, User user) {
    EmployeeRole employeeRole = employee.getEmployeeRole();
    Set<UserTypeRolePermission> userTypeRolePermissions = employeeRole.getUserTypeRolePermissions();
    updateUserTypeRolePermissions(userTypeRolePermissions, ispPlan, user);
  }

  private void updateUserTypeRolePermissions(Set<UserTypeRolePermission> userTypeRolePermissions, IspPlan ispPlan, User user) {
    Map<Long, Permission> permissionMap = ispPlan.getIspPlanPermissions()
            .stream()
            .map(IspPlanPermission::getPermission).collect(Collectors.toMap(Permission::getId, Function.identity()));
    Set<Long> collect = permissionMap.keySet();
    Set<Permission> permissions = new HashSet<>();
    for (UserTypeRolePermission userTypeRolePermission : userTypeRolePermissions) {
      if (!collect.contains(userTypeRolePermission.getPermissionId())) {
        userTypeRolePermissionRepository.delete(userTypeRolePermission);
      }else {
        permissions.add(permissionMap.get(userTypeRolePermission.getPermissionId()));
      }
    }
    user.setPermissions(permissions);
    userRepository.save(user);
  }
}
