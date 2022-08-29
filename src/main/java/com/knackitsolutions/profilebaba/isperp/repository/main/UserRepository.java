package com.knackitsolutions.profilebaba.isperp.repository.main;

import com.knackitsolutions.profilebaba.isperp.entity.main.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Boolean existsByPhoneNumber(String phoneNumber);

  Optional<User> findByPhoneNumber(String phoneNumber);

  Optional<User> findBySecondaryId(Long secondaryId);

  @Modifying
  @Query("update User i set i.isPhoneNumberVerified = :status where i.id = :id")
  @Transactional
  void validateUser(Boolean status, Long id);
}
