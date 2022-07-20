package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.dto.PlanDTO;
import com.knackitsolutions.profilebaba.isperp.entity.Plan;
import java.util.List;

public interface PlanService {

  Plan createPlan(PlanDTO request);

  void updatePlan(Long id, PlanDTO request);

  void deletePlan(Long id);

  PlanDTO getPlan(Long id);

  List<PlanDTO> getAllPlans();
}
