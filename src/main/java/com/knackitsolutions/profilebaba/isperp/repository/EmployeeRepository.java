package com.knackitsolutions.profilebaba.isperp.repository;

import com.knackitsolutions.profilebaba.isperp.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  Boolean existsByPhone(String phone);

}
