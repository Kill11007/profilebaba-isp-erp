package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.main.Vendor;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class VendorSpecification implements Specification<Vendor> {

  private final VendorQuery vendorQuery;

  @Override
  public Predicate toPredicate(Root<Vendor> root, CriteriaQuery<?> query,
      CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>(3);
    if (vendorQuery.getVendorId() != null) {
      predicates.add(criteriaBuilder.equal(root.get("id"), vendorQuery.getVendorId()));
    }

    if (vendorQuery.getPhone() != null) {
      predicates.add(criteriaBuilder.equal(root.get("phone"), vendorQuery.getPhone()));
    }

    if (vendorQuery.getUserId() != null) {
      predicates.add(criteriaBuilder.equal(root.get("userId"), vendorQuery.getUserId()));
    }

    if (vendorQuery.getBusinessName() != null) {
      predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("businessName")),
          "%" + vendorQuery.getBusinessName().toLowerCase(Locale.ROOT) + "%"));
    }
    return query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0]))).distinct(true)
        .getRestriction();
  }

}
