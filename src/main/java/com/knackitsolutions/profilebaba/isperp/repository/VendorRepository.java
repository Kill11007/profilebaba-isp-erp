package com.knackitsolutions.profilebaba.isperp.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.knackitsolutions.profilebaba.isperp.entity.Vendor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

  Boolean existsByPhoneNumber(String phoneNumber);

  Optional<Vendor> findByPhoneNumber(String phoneNumber);

  Boolean existsByBusinessName(String businessName);

  Boolean existsByPhoneNumberAndBusinessName(String phoneNumber, String businessName);

  @Modifying
  @Query("update Vendor i set i.isPhoneNumberVerified = :status where i.id = :id")
  @Transactional
  void validateVendor(Boolean status, Long id);
}