package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.tenant.Employee;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class EmployeeSpecification implements Specification<Employee> {

  private final EmployeeQuery employeeQuery;

  @Override
  public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query,
      CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();
    if (employeeQuery.getEmployeeId() != null) {
      predicates.add(criteriaBuilder.equal(root.get("id"), employeeQuery.getEmployeeId()));
    }

    if (employeeQuery.getName() != null) {
      predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
          "%" + employeeQuery.getName().toLowerCase() + "%"));
    }

    if (employeeQuery.getPhone() != null) {
      predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("phone")),
          "%" + employeeQuery.getPhone().toLowerCase() + "%"));
    }

    return query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0]))).distinct(true)
        .getRestriction();
  }

}
