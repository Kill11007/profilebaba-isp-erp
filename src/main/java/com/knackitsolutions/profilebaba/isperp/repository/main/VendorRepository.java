package com.knackitsolutions.profilebaba.isperp.repository.main;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.knackitsolutions.profilebaba.isperp.entity.main.Vendor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

  Boolean existsByBusinessName(String businessName);
}