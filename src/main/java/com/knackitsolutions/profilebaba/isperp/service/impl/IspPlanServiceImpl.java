package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.dto.IspPlanDTO;
import com.knackitsolutions.profilebaba.isperp.dto.IspPlanQuery;
import com.knackitsolutions.profilebaba.isperp.entity.main.IspPlan;
import com.knackitsolutions.profilebaba.isperp.entity.main.IspPlanPermission;
import com.knackitsolutions.profilebaba.isperp.entity.main.Vendor;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.VendorNotFoundException;
import com.knackitsolutions.profilebaba.isperp.repository.main.IspPlanPermissionRepository;
import com.knackitsolutions.profilebaba.isperp.repository.main.IspPlanRepository;
import com.knackitsolutions.profilebaba.isperp.repository.main.PermissionRepository;
import com.knackitsolutions.profilebaba.isperp.repository.main.VendorRepository;
import com.knackitsolutions.profilebaba.isperp.service.IspPlanService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IspPlanServiceImpl implements IspPlanService {

  private final IspPlanRepository repository;
  private final VendorRepository vendorRepository;
  private final PermissionRepository permissionRepository;
  private final IspPlanPermissionRepository ispPlanPermissionRepository;

  @Override
  public List<IspPlanDTO> all(IspPlanQuery ispPlanQuery) {
    return repository.findAll(ispPlanQuery.toSpecification()).stream().map(IspPlanDTO::new).collect(
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
    repository.save(one);
  }

  @Override
  public void create(IspPlanDTO dto) {
    IspPlan ispPlan = new IspPlan(dto);
    IspPlan save = repository.save(ispPlan);
    dto.getPermissionIds()
        .stream()
        .map(permissionRepository::findById)
        .flatMap(Optional::stream)
        .map(permission -> new IspPlanPermission(save, permission))
        .forEach(ispPlanPermissionRepository::save);
  }

  @Override
  public void delete(Long id) throws IspPlanNotFoundException {
    IspPlan one = one(id);
    repository.delete(one);
  }

  @Override
  public void activateIspPlan(Long ispId, Long planId)
      throws VendorNotFoundException, UserNotFoundException {
    Vendor vendor = vendorRepository.findById(ispId).orElseThrow(VendorNotFoundException::new);
    IspPlan one = one(planId);
    vendor.setIspPlan(one);
    vendorRepository.save(vendor);
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
    vendor.setIspPlan(null);
    vendorRepository.save(vendor);
  }

}
