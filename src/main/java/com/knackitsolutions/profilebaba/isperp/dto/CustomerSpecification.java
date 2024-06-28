package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.tenant.Customer;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class CustomerSpecification implements Specification<Customer> {

  private final CustomerQuery customerQuery;

  @Override
  public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query,
      CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();
    if (customerQuery.getCustomerId() != null) {
      predicates.add(criteriaBuilder.equal(root.get("id"), customerQuery.getCustomerId()));
    }

    if (customerQuery.getName() != null) {
      predicates.add(
          criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), customerQuery.getName()));
    }

    if (customerQuery.getPhone() != null) {
      predicates.add(
          criteriaBuilder.like(criteriaBuilder.lower(root.get("primaryMobileNo")), customerQuery.getPhone()));
    }

    if (customerQuery.getSecondaryPhone() != null) {
      predicates.add(
          criteriaBuilder.like(criteriaBuilder.lower(root.get("secondaryMobileNo")), customerQuery.getSecondaryPhone()));
    }

    if (customerQuery.getEmail() != null) {
      predicates.add(
          criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), customerQuery.getSecondaryPhone()));
    }
    return query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0]))).distinct(true)
        .getRestriction();
  }
}
