package com.knackitsolutions.profilebaba.isperp.listener;

import com.knackitsolutions.profilebaba.isperp.entity.main.VendorPlan;
import com.knackitsolutions.profilebaba.isperp.event.ISPPlanAssignmentEvent;
import com.knackitsolutions.profilebaba.isperp.event.ISPPlanDeactivateEvent;
import com.knackitsolutions.profilebaba.isperp.exception.PlanNotFoundException;
import com.knackitsolutions.profilebaba.isperp.repository.main.VendorPlanRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VendorPlanCreationListener {
  private final VendorPlanRepository vendorPlanRepository;

  @EventListener
  public void handleISPPlanAssignmentEvent(ISPPlanAssignmentEvent event) {
    List<VendorPlan> activePlans = event.getVendor().getVendorPlans().stream()
        .filter(vendorPlan -> vendorPlan.getEndDateTime() == null).toList();
    if (!activePlans.isEmpty()){
      throw new RuntimeException("Plan Already Assigned Plan-Id: " + activePlans.stream().findFirst().get().getPlan().getId());
    }
    VendorPlan vendorPlan = new VendorPlan(event.getVendor(), event.getIspPlan());
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
  }
}
