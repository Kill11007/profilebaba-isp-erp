package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.dto.PlanDTO;
import com.knackitsolutions.profilebaba.isperp.entity.Plan;
import com.knackitsolutions.profilebaba.isperp.service.PlanService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/plans")
@CrossOrigin("*")
@RequiredArgsConstructor
public class PlanController {

  private final PlanService planService;

  @GetMapping()
  public ResponseEntity<List<PlanDTO>> getPlans() {
    return ResponseEntity.ok(planService.getAllPlans());
  }

  @GetMapping("/{plan-id}")
  public ResponseEntity<PlanDTO> getPlan(@PathVariable("plan-id") Long planId) {
    return ResponseEntity.ok(planService.getPlan(planId));
  }

  @PostMapping
  public ResponseEntity<?> createPlan(@RequestBody PlanDTO request,
      UriComponentsBuilder uriComponentsBuilder) {
    Plan plan = planService.createPlan(request);
    UriComponents uriComponents = uriComponentsBuilder.path("/plans/{id}")
        .buildAndExpand(plan.getId());
    return ResponseEntity.created(uriComponents.toUri()).build();
  }

  @PutMapping("/{plan-id}")
  public ResponseEntity<?> updatePlan(@PathVariable("plan-id") Long planId,
      @RequestBody PlanDTO request) {
    planService.updatePlan(planId, request);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{plan-id}")
  public ResponseEntity<?> deletePlan(@PathVariable("plan-id") Long planId) {
    planService.deletePlan(planId);
    return ResponseEntity.noContent().build();
  }


}
