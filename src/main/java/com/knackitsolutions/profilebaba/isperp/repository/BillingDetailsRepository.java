package com.knackitsolutions.profilebaba.isperp.repository;

import com.knackitsolutions.profilebaba.isperp.entity.BillingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingDetailsRepository extends JpaRepository<BillingDetail, Long> {

}
