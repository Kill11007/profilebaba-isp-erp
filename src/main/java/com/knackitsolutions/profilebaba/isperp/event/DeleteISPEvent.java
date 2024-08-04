package com.knackitsolutions.profilebaba.isperp.event;

import com.knackitsolutions.profilebaba.isperp.entity.main.Vendor;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class DeleteISPEvent extends ApplicationEvent {
    private final Vendor vendor;

    public DeleteISPEvent(Object source, Vendor vendor) {
        super(source);
        this.vendor = vendor;
    }
}
