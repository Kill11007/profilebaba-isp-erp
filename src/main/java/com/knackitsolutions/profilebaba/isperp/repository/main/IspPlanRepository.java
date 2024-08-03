package com.knackitsolutions.profilebaba.isperp.repository.main;

import com.knackitsolutions.profilebaba.isperp.entity.main.IspPlan;
import com.knackitsolutions.profilebaba.isperp.enums.PlanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface IspPlanRepository extends JpaRepository<IspPlan, Long>, JpaSpecificationExecutor<IspPlan> {
    List<IspPlan> findByPlanType(PlanType planType);
    List<IspPlan> findByIsDefaultTrue();

}
