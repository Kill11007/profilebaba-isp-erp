package com.knackitsolutions.profilebaba.isperp.listener;

import com.knackitsolutions.profilebaba.isperp.entity.main.IspPlan;
import com.knackitsolutions.profilebaba.isperp.entity.main.VendorPlan;
import com.knackitsolutions.profilebaba.isperp.event.DefaultPlanChangeEvent;
import com.knackitsolutions.profilebaba.isperp.event.IspPlanChangeEvent;
import com.knackitsolutions.profilebaba.isperp.repository.main.VendorPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultPlanChangeEventListener {
    private final VendorPlanRepository vendorPlanRepository;
    private final ApplicationEventPublisher publisher;

    @EventListener
    public void handleDefaultPlanChangeEvent(DefaultPlanChangeEvent event) {
        IspPlan plan = event.getPlan();
        IspPlan oldPlan = event.getOldPlan();
        List<VendorPlan> oldVendorPlans = vendorPlanRepository.findAllByPlanId(oldPlan.getId());
        for (VendorPlan vendorPlan : oldVendorPlans) {
            publisher.publishEvent(new IspPlanChangeEvent(this, plan, vendorPlan));
        }
    }
}
