package com.knackitsolutions.profilebaba.isperp.event;

import com.knackitsolutions.profilebaba.isperp.entity.main.Vendor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class ISPPlanDeactivateEvent extends ApplicationEvent {

  private final Vendor vendor;
  public ISPPlanDeactivateEvent(Object source, Vendor vendor) {
    super(source);
    this.vendor = vendor;
  }
}
