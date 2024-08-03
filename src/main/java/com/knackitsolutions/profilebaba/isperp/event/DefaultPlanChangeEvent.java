package com.knackitsolutions.profilebaba.isperp.event;

import com.knackitsolutions.profilebaba.isperp.entity.main.IspPlan;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class DefaultPlanChangeEvent extends ApplicationEvent {
    private final IspPlan plan;
    private final IspPlan oldPlan;

    public DefaultPlanChangeEvent(Object source, IspPlan plan, IspPlan oldDefaultPlan) {
        super(source);
        this.plan = plan;
        this.oldPlan = oldDefaultPlan;
    }
}
