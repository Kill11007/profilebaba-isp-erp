package com.knackitsolutions.profilebaba.isperp.repository.main;

import com.knackitsolutions.profilebaba.isperp.entity.main.IspPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IspPlanRepository extends JpaRepository<IspPlan, Long>, JpaSpecificationExecutor<IspPlan> {

}
