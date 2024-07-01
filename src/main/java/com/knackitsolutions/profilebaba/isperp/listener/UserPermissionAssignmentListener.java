package com.knackitsolutions.profilebaba.isperp.listener;

import com.knackitsolutions.profilebaba.isperp.entity.main.IspPlan;
import com.knackitsolutions.profilebaba.isperp.entity.main.IspPlanPermission;
import com.knackitsolutions.profilebaba.isperp.entity.main.User;
import com.knackitsolutions.profilebaba.isperp.entity.main.Vendor;
import com.knackitsolutions.profilebaba.isperp.entity.main.VendorPlan;
import com.knackitsolutions.profilebaba.isperp.event.ISPPlanAssignmentEvent;
import com.knackitsolutions.profilebaba.isperp.event.ISPPlanDeactivateEvent;
import com.knackitsolutions.profilebaba.isperp.exception.PlanNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.UserService;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPermissionAssignmentListener {

  private final UserService userService;

  @EventListener
  public void handleISPPlanAssignmentEvent(ISPPlanAssignmentEvent event)
      throws UserNotFoundException {
    IspPlan ispPlan = event.getIspPlan();
    Vendor vendor = event.getVendor();
    Long userId = vendor.getUserId();
    User user = userService.findById(userId);
    user.setPermissions(ispPlan.getIspPlanPermissions().stream()
        .map(IspPlanPermission::getPermission)
        .collect(Collectors.toSet()));
    userService.save(user);
  }

  @EventListener
  public void handleISPPlanDeactivateEvent(ISPPlanDeactivateEvent event)
      throws UserNotFoundException {
    Vendor vendor = event.getVendor();
    User user = userService.findById(vendor.getUserId());
//    String tenantId = user.getTenantId();
    user.setPermissions(null);
    userService.save(user);
  }

}
