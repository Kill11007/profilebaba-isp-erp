package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.dto.ServiceAreaDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.ServiceArea;
import com.knackitsolutions.profilebaba.isperp.exception.ServiceAreaAlreadyExistsException;
import com.knackitsolutions.profilebaba.isperp.exception.ServiceAreaNotFoundException;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.ServiceAreaRepository;
import com.knackitsolutions.profilebaba.isperp.service.AreaService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {

  private final ServiceAreaRepository repository;

  @Override
  public ServiceArea add(ServiceAreaDTO serviceArea) throws ServiceAreaAlreadyExistsException {
    if (repository.existsByName(serviceArea.getName())) {
      throw new ServiceAreaAlreadyExistsException();
    }
    return save(new ServiceArea(serviceArea));
  }

  @Override
  public ServiceArea save(ServiceArea serviceArea) {
    return repository.save(serviceArea);
  }

  @Override
  public ServiceAreaDTO findById(Long id) throws ServiceAreaNotFoundException {
    return new ServiceAreaDTO(get(id));
  }

  @Override
  public ServiceArea get(Long id) throws ServiceAreaNotFoundException{
    return repository.findById(id).orElseThrow(ServiceAreaNotFoundException::new);
  }

  @Override
  public List<ServiceAreaDTO> findAll() {
    return repository.findAll().stream().map(ServiceAreaDTO::new).collect(Collectors.toList());
  }

  @Override
  public void delete(Long id) throws ServiceAreaNotFoundException {
    ServiceArea serviceArea = get(id);
    delete(serviceArea);
  }

  @Override
  public void delete(ServiceArea serviceArea) {
    repository.delete(serviceArea);
  }

  @Override
  public void update(Long id, ServiceAreaDTO serviceAreaDTO) throws ServiceAreaNotFoundException {
    ServiceArea serviceArea = get(id);
    serviceArea.update(serviceAreaDTO);
    save(serviceArea);
  }
}
