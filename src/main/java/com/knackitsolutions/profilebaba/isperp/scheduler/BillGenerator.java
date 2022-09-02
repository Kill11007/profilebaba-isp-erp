package com.knackitsolutions.profilebaba.isperp.scheduler;

import com.knackitsolutions.profilebaba.isperp.entity.tenant.Bill;
import com.knackitsolutions.profilebaba.isperp.service.BillService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BillGenerator {

  private final BillService billService;

  @Scheduled(cron = "${bill-generator.cron.expression}")
  public void generateBills() {
    List<Bill> bills = billService.generateBills();
  }


}
