package com.knackitsolutions.profilebaba.isperp.event;

import com.knackitsolutions.profilebaba.isperp.entity.main.IspPlan;
import com.knackitsolutions.profilebaba.isperp.entity.main.Vendor;
import lombok.Data;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ISPPlanAssignmentEvent extends ApplicationEvent {

  private final Vendor vendor;
  private final IspPlan ispPlan;

  public ISPPlanAssignmentEvent(Object source, Vendor vendor, IspPlan ispPlan) {
    super(source);
    this.vendor = vendor;
    this.ispPlan = ispPlan;
  }
}
