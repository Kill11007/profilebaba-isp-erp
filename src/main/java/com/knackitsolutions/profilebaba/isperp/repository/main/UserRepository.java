package com.knackitsolutions.profilebaba.isperp.repository.main;

import com.knackitsolutions.profilebaba.isperp.entity.main.User;
import java.util.List;
import java.util.Optional;

import com.knackitsolutions.profilebaba.isperp.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Boolean existsByPhoneNumber(String phoneNumber);
  Optional<User> findByPhoneNumberAndTenantId(String phoneNumber, String tenantId);
  List<Optional<User>> findByPhoneNumber(String phoneNumber);
  @Modifying
  @Query("update User i set i.isPhoneNumberVerified = :status where i.id = :id")
  @Transactional
  void validateUser(Boolean status, Long id);

  List<User> findAllByTenantId(String tenantId);
  List<User> findAllByTenantIdAndUserType(String tenantId, UserType userType);

}
