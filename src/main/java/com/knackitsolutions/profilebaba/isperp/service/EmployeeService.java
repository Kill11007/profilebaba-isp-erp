package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.dto.EmployeeDTO;
import com.knackitsolutions.profilebaba.isperp.dto.NewEmployeeRequest;
import com.knackitsolutions.profilebaba.isperp.entity.Employee;
import com.knackitsolutions.profilebaba.isperp.exception.EmployeeAlreadyExistsException;
import com.knackitsolutions.profilebaba.isperp.exception.EmployeeNotFoundException;
import java.util.List;

public interface EmployeeService {

  Employee add(NewEmployeeRequest request) throws EmployeeAlreadyExistsException;

  void update(Long id, EmployeeDTO dto) throws EmployeeNotFoundException;

  EmployeeDTO one(Long id) throws EmployeeNotFoundException;

  List<EmployeeDTO> all();

  void delete(Long id) throws EmployeeNotFoundException;


  Employee save(Employee employee);

  Employee findById(Long id) throws EmployeeNotFoundException;

}
