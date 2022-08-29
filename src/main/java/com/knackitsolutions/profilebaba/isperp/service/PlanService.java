package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.dto.PlanDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Plan;
import com.knackitsolutions.profilebaba.isperp.exception.PlanNotFoundException;
import java.util.List;

public interface PlanService {

  Plan createPlan(PlanDTO request);

  void updatePlan(Long id, PlanDTO request);

  void deletePlan(Long id);

  PlanDTO getPlan(Long id);

  List<PlanDTO> getAllPlans();

  void updateStatus(Long id);

  Plan getPlanById(Long id) throws PlanNotFoundException;
}
