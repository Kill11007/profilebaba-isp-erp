package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.entity.main.Tenant;

public interface TenantManagementService {
  Tenant createTenant(String tenantId, String db, String password);

}
