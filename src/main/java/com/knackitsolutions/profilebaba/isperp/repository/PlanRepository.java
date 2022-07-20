package com.knackitsolutions.profilebaba.isperp.repository;

import com.knackitsolutions.profilebaba.isperp.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

}
