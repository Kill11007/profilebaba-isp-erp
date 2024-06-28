package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.main.IspPlan;
import com.knackitsolutions.profilebaba.isperp.enums.PlanType;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class IspPlanSpecification implements Specification<IspPlan> {

  private final IspPlanQuery ispPlanQuery;

  @Override
  public Predicate toPredicate(Root<IspPlan> root, CriteriaQuery<?> query,
      CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>(4);
    if (ispPlanQuery.getPlanId() != null) {
      predicates.add(criteriaBuilder.equal(root.get("id"), ispPlanQuery.getPlanId()));
    }

    if (ispPlanQuery.getPlanType() != null && PlanType.of(ispPlanQuery.getPlanType()) != null) {
      predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("planType")),
          "%" + ispPlanQuery.getPlanType() + "%"));
    }

    if (ispPlanQuery.getName() != null) {
      predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
          "%" + ispPlanQuery.getName() + "%"));
    }

    if (ispPlanQuery.getDescription() != null) {
      predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("planDescription")),
          "%" + ispPlanQuery.getDescription() + "%"));
    }

    return query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0]))).distinct(true)
        .getRestriction();

  }
}
