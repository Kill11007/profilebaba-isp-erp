package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.dto.InternetPlanDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.InternetPlan;
import com.knackitsolutions.profilebaba.isperp.exception.PlanNotFoundException;
import java.util.List;

public interface InternetPlanService {

  InternetPlan createPlan(InternetPlanDTO request);

  void updatePlan(Long id, InternetPlanDTO request);

  void deletePlan(Long id);

  InternetPlanDTO getPlan(Long id);

  List<InternetPlanDTO> getAllPlans();

  void updateStatus(Long id);

  InternetPlan getPlanById(Long id) throws PlanNotFoundException;
}
