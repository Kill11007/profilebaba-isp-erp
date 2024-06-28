package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.main.Tenant;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class TenantSpecification implements Specification<Tenant> {

  private final String tenantId;

  @Override
  public Predicate toPredicate(Root<Tenant> root, CriteriaQuery<?> query,
      CriteriaBuilder criteriaBuilder) {
    return criteriaBuilder.equal(root.get("tenantId"), tenantId);
  }

}
