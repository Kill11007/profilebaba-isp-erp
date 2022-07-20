package com.knackitsolutions.profilebaba.isperp.repository;

import com.knackitsolutions.profilebaba.isperp.entity.HardwareDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HardwareDetailRepository extends JpaRepository<HardwareDetail, Long> {

}
