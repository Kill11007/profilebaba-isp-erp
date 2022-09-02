package com.knackitsolutions.profilebaba.isperp.repository.tenant;

import com.knackitsolutions.profilebaba.isperp.entity.tenant.BalanceSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceSheetRepository extends JpaRepository<BalanceSheet, Long> {

}
