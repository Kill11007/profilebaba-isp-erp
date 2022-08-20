package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.dto.EmployeeDTO;
import com.knackitsolutions.profilebaba.isperp.dto.NewEmployeeRequest;
import com.knackitsolutions.profilebaba.isperp.entity.Employee;
import com.knackitsolutions.profilebaba.isperp.exception.EmployeeAlreadyExistsException;
import com.knackitsolutions.profilebaba.isperp.exception.EmployeeNotFoundException;
import com.knackitsolutions.profilebaba.isperp.repository.EmployeeRepository;
import com.knackitsolutions.profilebaba.isperp.service.EmployeeService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository repository;

  @Override
  public Employee add(NewEmployeeRequest request) throws EmployeeAlreadyExistsException {
    if (repository.existsByPhone(request.getPhone())) {
      throw new EmployeeAlreadyExistsException(
          "Phone number already exists. Please provide new phone number.");
    }
    return save(new Employee(request));
  }

  @Override
  public void update(Long id, EmployeeDTO dto) throws EmployeeNotFoundException {
    Employee employee = findById(id);
    employee.update(dto);
    save(employee);
  }

  @Override
  public EmployeeDTO one(Long id) throws EmployeeNotFoundException {
    return new EmployeeDTO(findById(id));
  }

  @Override
  public List<EmployeeDTO> all() {
    return repository.findAll().stream().map(EmployeeDTO::new).collect(Collectors.toList());
  }

  @Override
  public void delete(Long id) throws EmployeeNotFoundException {
    Employee byId = findById(id);
    repository.delete(byId);
  }

  @Override
  public Employee save(Employee employee) {
    return repository.save(employee);
  }

  @Override
  public Employee findById(Long id) throws EmployeeNotFoundException {
    return repository.findById(id).orElseThrow(EmployeeNotFoundException::new);
  }
}
