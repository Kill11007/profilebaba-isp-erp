package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.dto.IspPlanDTO;
import com.knackitsolutions.profilebaba.isperp.dto.IspPlanQuery;
import com.knackitsolutions.profilebaba.isperp.dto.IspPlanSpecification;
import com.knackitsolutions.profilebaba.isperp.entity.main.IspPlan;
import com.knackitsolutions.profilebaba.isperp.entity.main.IspPlanPermission;
import com.knackitsolutions.profilebaba.isperp.entity.main.Permission;
import com.knackitsolutions.profilebaba.isperp.entity.main.Vendor;
import com.knackitsolutions.profilebaba.isperp.entity.main.VendorPlan;
import com.knackitsolutions.profilebaba.isperp.event.ISPPlanAssignmentEvent;
import com.knackitsolutions.profilebaba.isperp.event.ISPPlanDeactivateEvent;
import com.knackitsolutions.profilebaba.isperp.exception.PlanNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.VendorNotFoundException;
import com.knackitsolutions.profilebaba.isperp.repository.main.IspPlanPermissionRepository;
import com.knackitsolutions.profilebaba.isperp.repository.main.IspPlanRepository;
import com.knackitsolutions.profilebaba.isperp.repository.main.PermissionRepository;
import com.knackitsolutions.profilebaba.isperp.repository.main.VendorPlanRepository;
import com.knackitsolutions.profilebaba.isperp.repository.main.VendorRepository;
import com.knackitsolutions.profilebaba.isperp.service.IspPlanService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IspPlanServiceImpl implements IspPlanService {

  private final IspPlanRepository repository;
  private final VendorRepository vendorRepository;
  private final PermissionRepository permissionRepository;
  private final IspPlanPermissionRepository ispPlanPermissionRepository;
  private final VendorPlanRepository vendorPlanRepository;

  private final ApplicationEventPublisher eventPublisher;

  @Override
  public List<IspPlanDTO> all() {
    return repository.findAll().stream().map(IspPlanDTO::new).collect(
        Collectors.toList());
  }

  @Override
  public IspPlanDTO oneDTO(Long id) throws IspPlanNotFoundException {
    return repository.findById(id).map(IspPlanDTO::new).orElseThrow(IspPlanNotFoundException::new);
  }

  @Override
  public IspPlan one(Long id) throws IspPlanNotFoundException {
    return repository.findById(id).orElseThrow(IspPlanNotFoundException::new);
  }

  @Override
  public void update(Long id, IspPlanDTO dto) throws IspPlanNotFoundException {
    IspPlan one = one(id);
    one.update(dto);
    one.getIspPlanPermissions().clear();
    one.setIspPlanPermissions(dto.getPermissionIds().stream().map(permissionRepository::findById)
        .flatMap(Optional::stream)
        .map(permission -> new IspPlanPermission(one, permission)).collect(Collectors.toSet()));
    repository.save(one);
  }

  @Override
  public void create(IspPlanDTO dto) {
    IspPlan ispPlan = new IspPlan(dto);
    Set<IspPlanPermission> ispPlanPermissions = dto.getPermissionIds()
        .stream()
        .map(permissionRepository::findById)
        .flatMap(Optional::stream)
        .map(permission -> new IspPlanPermission(ispPlan, permission)).collect(Collectors.toSet());
    ispPlan.setIspPlanPermissions(ispPlanPermissions);
    IspPlan save = repository.save(ispPlan);
  }

  //Do not delete a plan. Many customer would already be using this plan.
  @Override
  public void delete(Long id) throws IspPlanNotFoundException {
    IspPlan one = one(id);
    ispPlanPermissionRepository.deleteAll(one.getIspPlanPermissions());
    one.getVendorPlans().stream()
        .peek(vp -> eventPublisher.publishEvent(new ISPPlanDeactivateEvent(this, vp.getVendor())))
        .forEach(vendorPlanRepository::delete);
    repository.delete(one);
  }

  @Override
  public void activateIspPlan(Long ispId, Long planId)
      throws VendorNotFoundException {
    Vendor vendor = vendorRepository.findById(ispId).orElseThrow(VendorNotFoundException::new);
    IspPlan one = one(planId);
    eventPublisher.publishEvent(new ISPPlanAssignmentEvent(this, vendor, one));
  }

  @Override
  public void activateIspFreePlan(Long ispId) throws VendorNotFoundException, UserNotFoundException {
//    IspPlanQuery free = new IspPlanQuery(null, null, null, "FREE");
    // TODO GET FREE PLAN ID
    activateIspPlan(ispId, 1L);
  }

  @Override
  public void deactivateCurrentIspPlan(Long ispId) throws VendorNotFoundException {
    Vendor vendor = vendorRepository.findById(ispId).orElseThrow(VendorNotFoundException::new);
    eventPublisher.publishEvent(new ISPPlanDeactivateEvent(this, vendor));
  }

}
