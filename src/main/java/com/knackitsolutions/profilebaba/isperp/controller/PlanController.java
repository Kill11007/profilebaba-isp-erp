package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.dto.PlanDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.InternetPlan;
import com.knackitsolutions.profilebaba.isperp.service.InternetPlanService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@CrossOrigin
@RequiredArgsConstructor
public class PlanController {

  private final InternetPlanService internetPlanService;

  @GetMapping()
  public ResponseEntity<List<PlanDTO>> getPlans() {
    return ResponseEntity.ok(internetPlanService.getAllPlans());
  }

  @GetMapping("/{plan-id}")
  public ResponseEntity<PlanDTO> getPlan(@PathVariable("plan-id") Long planId) {
    return ResponseEntity.ok(internetPlanService.getPlan(planId));
  }

  @PostMapping
  public ResponseEntity<?> createPlan(@RequestBody PlanDTO request,
      UriComponentsBuilder uriComponentsBuilder) {
    InternetPlan internetPlan = internetPlanService.createPlan(request);
    UriComponents uriComponents = uriComponentsBuilder.path("/plans/{id}")
        .buildAndExpand(internetPlan.getId());
    return ResponseEntity.created(uriComponents.toUri()).build();
  }

  @PutMapping("/{plan-id}")
  public ResponseEntity<?> updatePlan(@PathVariable("plan-id") Long planId,
      @RequestBody PlanDTO request) {
    internetPlanService.updatePlan(planId, request);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{plan-id}")
  public ResponseEntity<?> deletePlan(@PathVariable("plan-id") Long planId) {
    internetPlanService.deletePlan(planId);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{plan-id}")
  public ResponseEntity<?> activatePlan(@PathVariable("plan-id") Long planId) {
    internetPlanService.updateStatus(planId);
    return ResponseEntity.noContent().build();
  }


}
