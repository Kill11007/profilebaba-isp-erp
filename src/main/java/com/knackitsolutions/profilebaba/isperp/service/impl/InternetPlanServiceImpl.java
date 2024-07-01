package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.dto.PlanDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.InternetPlan;
import com.knackitsolutions.profilebaba.isperp.exception.PlanNotFoundException;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.PlanRepository;
import com.knackitsolutions.profilebaba.isperp.service.InternetPlanService;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InternetPlanServiceImpl implements InternetPlanService {

  private final PlanRepository planRepository;

  @Override
  public InternetPlan createPlan(PlanDTO request) {
    InternetPlan internetPlan = new InternetPlan(request);
    return planRepository.save(internetPlan);
  }

  @Override
  public void updatePlan(Long id, PlanDTO request) {
    InternetPlan internetPlan = getPlanById(id);
    internetPlan.updatePlan(request);
    planRepository.save(internetPlan);
  }

  @Override
  public void deletePlan(Long id) {
    InternetPlan internetPlan = getPlanById(id);
    planRepository.delete(internetPlan);
  }

  @Override
  public PlanDTO getPlan(Long id) {
    InternetPlan internetPlan = getPlanById(id);
    return new PlanDTO(internetPlan);
  }

  @Override
  public List<PlanDTO> getAllPlans() {
    return planRepository.findAll().stream().map(PlanDTO::new).collect(Collectors.toList());
  }

  @Override
  public void updateStatus(Long id) {
    InternetPlan internetPlan = getPlanById(id);
    internetPlan.setActive(!internetPlan.getActive());
    planRepository.save(internetPlan);
  }

  @Override
  public InternetPlan getPlanById(Long id) throws PlanNotFoundException {
    return planRepository.findById(id).orElseThrow(() -> planNotFoundWithId.apply(id));
  }

  private final Function<Long, PlanNotFoundException> planNotFoundWithId = id -> new PlanNotFoundException(
      "Plan not found with ID: " + id);
}
