package com.knackitsolutions.profilebaba.isperp.event;

import com.knackitsolutions.profilebaba.isperp.entity.main.IspPlan;
import com.knackitsolutions.profilebaba.isperp.entity.main.Vendor;
import com.knackitsolutions.profilebaba.isperp.entity.main.VendorPlan;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class IspPlanChangeEvent extends ApplicationEvent {
    private final VendorPlan vendorPlan;
    private final IspPlan plan;

    public IspPlanChangeEvent(Object source, IspPlan plan, VendorPlan vendorPlan) {
        super(source);
        this.plan = plan;
        this.vendorPlan = vendorPlan;
    }
}
