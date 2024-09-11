package com.knackitsolutions.profilebaba.isperp.repository.main;

import com.knackitsolutions.profilebaba.isperp.entity.main.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
    Optional<AdminUser> findByUserId(Long userId);
}
