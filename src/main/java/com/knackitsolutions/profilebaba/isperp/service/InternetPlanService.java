package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.dto.PlanDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.InternetPlan;
import com.knackitsolutions.profilebaba.isperp.exception.PlanNotFoundException;
import java.util.List;

public interface InternetPlanService {

  InternetPlan createPlan(PlanDTO request);

  void updatePlan(Long id, PlanDTO request);

  void deletePlan(Long id);

  PlanDTO getPlan(Long id);

  List<PlanDTO> getAllPlans();

  void updateStatus(Long id);

  InternetPlan getPlanById(Long id) throws PlanNotFoundException;
}
