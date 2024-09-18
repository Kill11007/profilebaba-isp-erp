package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.dto.IspPlanDTO;
import com.knackitsolutions.profilebaba.isperp.dto.IspPlanQuery;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.VendorNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.IspPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/isp-plans")
@CrossOrigin("*")
@RequiredArgsConstructor
public class IspPlanController {

  private final IspPlanService ispPlanService;

  @PostMapping
  public ResponseEntity<Void> newPlan(@RequestBody IspPlanDTO dto) {
    ispPlanService.create(dto);
    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity<Page<IspPlanDTO>> getAll(
      @RequestBody(required = false) IspPlanQuery query, @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "1000") Integer size) {
    return ResponseEntity.ok(ispPlanService.all(page, size));
  }

  @GetMapping("/{plan-id}")
  public ResponseEntity<IspPlanDTO> getAll(@PathVariable("plan-id") Long planId) {
    return ResponseEntity.ok(ispPlanService.oneDTO(planId));
  }

  @PutMapping("/{plan-id}")
  public ResponseEntity<Void> update(@PathVariable("plan-id") Long planId,
      @RequestBody IspPlanDTO dto) {
    ispPlanService.update(planId, dto);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{plan-id}")
  public ResponseEntity<Void> delete(@PathVariable("plan-id") Long planId) {
    ispPlanService.delete(planId);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/isp/{isp-id}/plan/{plan-id}")
  public ResponseEntity<Void> activateIspPlan(@PathVariable("isp-id") Long ispId,
      @PathVariable("plan-id") Long planId) throws VendorNotFoundException, UserNotFoundException {
    ispPlanService.activateIspPlan(ispId, planId);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{plan-id}/default")
  public ResponseEntity<Void> activateIspPlan(@PathVariable("plan-id") Long planId) {
    ispPlanService.makePlanAsDefault(planId);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/isp/{isp-id}")
  public ResponseEntity<Void> deactivateCurrentIspPlan(@PathVariable("isp-id") Long ispId)
      throws VendorNotFoundException {
    ispPlanService.deactivateCurrentIspPlan(ispId);
    return ResponseEntity.ok().build();
  }


}
