package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.dto.InternetPlanDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.InternetPlan;
import com.knackitsolutions.profilebaba.isperp.exception.PlanNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InternetPlanService {

  InternetPlan createPlan(InternetPlanDTO request);

  void updatePlan(Long id, InternetPlanDTO request);

  void deletePlan(Long id);

  InternetPlanDTO getPlan(Long id);

  Page<InternetPlanDTO> getAllPlans(Integer page, Integer size);

  void updateStatus(Long id);

  InternetPlan getPlanById(Long id) throws PlanNotFoundException;
}
