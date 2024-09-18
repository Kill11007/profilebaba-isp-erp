package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.dto.InternetPlanDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.InternetPlan;
import com.knackitsolutions.profilebaba.isperp.exception.PlanNotFoundException;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.InternetPlanRepository;
import com.knackitsolutions.profilebaba.isperp.service.InternetPlanService;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InternetPlanServiceImpl implements InternetPlanService {

  private final InternetPlanRepository internetPlanRepository;

  @Override
  public InternetPlan createPlan(InternetPlanDTO request) {
    InternetPlan internetPlan = new InternetPlan(request);
    return internetPlanRepository.save(internetPlan);
  }

  @Override
  public void updatePlan(Long id, InternetPlanDTO request) {
    InternetPlan internetPlan = getPlanById(id);
    internetPlan.updatePlan(request);
    internetPlanRepository.save(internetPlan);
  }

  //Be careful when deleting there could be customers on that plan. So remove those first.
  @Override
  public void deletePlan(Long id) {
    InternetPlan internetPlan = getPlanById(id);
    internetPlanRepository.delete(internetPlan);
  }

  @Override
  public InternetPlanDTO getPlan(Long id) {
    InternetPlan internetPlan = getPlanById(id);
    return new InternetPlanDTO(internetPlan);
  }

  @Override
  public Page<InternetPlanDTO> getAllPlans(Integer page, Integer size) {
    return internetPlanRepository.findAll(PageRequest.of(page, size)).map(InternetPlanDTO::new);
  }

  @Override
  public void updateStatus(Long id) {
    InternetPlan internetPlan = getPlanById(id);
    internetPlan.setActive(!internetPlan.getActive());
    internetPlanRepository.save(internetPlan);
  }

  @Override
  public InternetPlan getPlanById(Long id) throws PlanNotFoundException {
    return internetPlanRepository.findById(id).orElseThrow(() -> planNotFoundWithId.apply(id));
  }

  private final Function<Long, PlanNotFoundException> planNotFoundWithId = id -> new PlanNotFoundException(
      "Plan not found with ID: " + id);
}
