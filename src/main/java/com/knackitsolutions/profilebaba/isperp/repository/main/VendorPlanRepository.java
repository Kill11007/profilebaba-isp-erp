package com.knackitsolutions.profilebaba.isperp.repository.main;

import com.knackitsolutions.profilebaba.isperp.entity.main.VendorPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorPlanRepository extends JpaRepository<VendorPlan, Long> {
    List<VendorPlan> findAllByPlanId(Long planId);
}
