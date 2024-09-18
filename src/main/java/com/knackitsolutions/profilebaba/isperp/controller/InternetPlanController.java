package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.dto.InternetPlanDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.InternetPlan;
import com.knackitsolutions.profilebaba.isperp.service.InternetPlanService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/internet-plans")
@CrossOrigin
@RequiredArgsConstructor
public class InternetPlanController {

  private final InternetPlanService internetPlanService;

  @GetMapping()
  public ResponseEntity<Page<InternetPlanDTO>> getPlans(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                        @RequestParam(required = false, defaultValue = "1000") Integer size) {
    return ResponseEntity.ok(internetPlanService.getAllPlans(page, size));
  }

  @GetMapping("/{plan-id}")
  public ResponseEntity<InternetPlanDTO> getPlan(@PathVariable("plan-id") Long planId) {
    return ResponseEntity.ok(internetPlanService.getPlan(planId));
  }

  @PostMapping
  public ResponseEntity<?> createPlan(@RequestBody InternetPlanDTO request,
      UriComponentsBuilder uriComponentsBuilder) {
    InternetPlan internetPlan = internetPlanService.createPlan(request);
    UriComponents uriComponents = uriComponentsBuilder.path("/internet-plans/{id}")
        .buildAndExpand(internetPlan.getId());
    return ResponseEntity.created(uriComponents.toUri()).build();
  }

  @PutMapping("/{plan-id}")
  public ResponseEntity<?> updatePlan(@PathVariable("plan-id") Long planId,
      @RequestBody InternetPlanDTO request) {
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
