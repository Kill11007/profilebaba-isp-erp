package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.dto.PlanDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Plan;
import com.knackitsolutions.profilebaba.isperp.exception.PlanNotFoundException;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.PlanRepository;
import com.knackitsolutions.profilebaba.isperp.service.PlanService;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

  private final PlanRepository planRepository;

  @Override
  public Plan createPlan(PlanDTO request) {
    Plan plan = new Plan(request);
    return planRepository.save(plan);
  }

  @Override
  public void updatePlan(Long id, PlanDTO request) {
    Plan plan = getPlanById(id);
    plan.updatePlan(request);
    planRepository.save(plan);
  }

  @Override
  public void deletePlan(Long id) {
    Plan plan = getPlanById(id);
    planRepository.delete(plan);
  }

  @Override
  public PlanDTO getPlan(Long id) {
    Plan plan = getPlanById(id);
    return new PlanDTO(plan);
  }

  @Override
  public List<PlanDTO> getAllPlans() {
    return planRepository.findAll().stream().map(PlanDTO::new).collect(Collectors.toList());
  }

  @Override
  public void updateStatus(Long id) {
    Plan plan = getPlanById(id);
    plan.setActive(!plan.getActive());
    planRepository.save(plan);
  }

  @Override
  public Plan getPlanById(Long id) throws PlanNotFoundException {
    return planRepository.findById(id).orElseThrow(() -> planNotFoundWithId.apply(id));
  }

  private final Function<Long, PlanNotFoundException> planNotFoundWithId = id -> new PlanNotFoundException(
      "Plan not found with ID: " + id);
}
