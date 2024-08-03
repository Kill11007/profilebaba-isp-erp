package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.dto.IspPlanDTO;
import com.knackitsolutions.profilebaba.isperp.entity.main.IspPlan;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.VendorNotFoundException;
import java.util.List;

public interface IspPlanService {

  List<IspPlanDTO> all();

  IspPlanDTO oneDTO(Long id) throws IspPlanNotFoundException;

  IspPlan one(Long id) throws IspPlanNotFoundException;

  void update(Long id, IspPlanDTO dto) throws IspPlanNotFoundException;

  void create(IspPlanDTO dto);

  void delete(Long id) throws IspPlanNotFoundException;

  void activateIspPlan(Long ispId, Long planId)
      throws VendorNotFoundException, UserNotFoundException;

  void activateIspDefaultPlan(Long ispId) throws VendorNotFoundException, UserNotFoundException;

  void makePlanAsDefault(Long planId);

  void deactivateCurrentIspPlan(Long ispId) throws VendorNotFoundException;

  class IspPlanNotFoundException extends RuntimeException{

  }
}
