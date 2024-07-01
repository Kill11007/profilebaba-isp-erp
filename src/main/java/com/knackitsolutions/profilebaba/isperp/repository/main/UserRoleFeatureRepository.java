package com.knackitsolutions.profilebaba.isperp.repository.main;

import com.knackitsolutions.profilebaba.isperp.entity.main.UserRoleFeature;
import com.knackitsolutions.profilebaba.isperp.enums.UserType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleFeatureRepository extends JpaRepository<UserRoleFeature, Long> {

  List<UserRoleFeature> findByUserType(UserType userType);
}
