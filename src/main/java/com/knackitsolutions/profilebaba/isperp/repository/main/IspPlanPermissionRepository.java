package com.knackitsolutions.profilebaba.isperp.repository.main;

import com.knackitsolutions.profilebaba.isperp.entity.main.IspPlanPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IspPlanPermissionRepository extends JpaRepository<IspPlanPermission, Long>,
    JpaSpecificationExecutor<IspPlanPermission> {

}
