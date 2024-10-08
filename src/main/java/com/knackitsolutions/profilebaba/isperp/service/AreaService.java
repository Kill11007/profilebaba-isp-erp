package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.dto.ServiceAreaDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.ServiceArea;
import com.knackitsolutions.profilebaba.isperp.exception.ServiceAreaAlreadyExistsException;
import com.knackitsolutions.profilebaba.isperp.exception.ServiceAreaNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AreaService {

  ServiceArea add(ServiceAreaDTO serviceArea) throws ServiceAreaAlreadyExistsException;

  ServiceArea save(ServiceArea serviceArea);

  ServiceAreaDTO findById(Long id) throws ServiceAreaNotFoundException;
  ServiceArea get(Long id) throws ServiceAreaNotFoundException;

  Page<ServiceAreaDTO> findAll(Integer page, Integer size);

  void delete(Long id) throws ServiceAreaNotFoundException;

  void delete(ServiceArea serviceArea);

  void update(Long id, ServiceAreaDTO serviceAreaDTO) throws ServiceAreaNotFoundException;

}
