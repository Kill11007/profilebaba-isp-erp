package com.knackitsolutions.profilebaba.isperp.scheduler;

import com.knackitsolutions.profilebaba.isperp.config.TenantContext;
import com.knackitsolutions.profilebaba.isperp.entity.main.Tenant;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Bill;
import com.knackitsolutions.profilebaba.isperp.service.BillService;
import com.knackitsolutions.profilebaba.isperp.service.TenantManagementService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "bill-generator.scheduler", name = "enable", havingValue = "true")
public class BillGenerator {

  private final BillService billService;
  private final TenantManagementService tenantManagementService;

  @Scheduled(cron = "${bill-generator.cron.expression}")
  public void generateBills() {
    for (Tenant tenant : tenantManagementService.allTenants()) {
      if (tenant.getDb().equalsIgnoreCase("BOOTSTRAP")){continue;}
      TenantContext.setTenantId(tenant.getTenantId());
      List<Bill> bills = billService.generateBills();
      TenantContext.clear();
    }
  }


}
