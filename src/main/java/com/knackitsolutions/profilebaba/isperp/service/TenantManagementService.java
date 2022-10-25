package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.entity.main.Tenant;
import java.util.List;

public interface TenantManagementService {
  Tenant createTenant(String tenantId, String db, String password);

  List<Tenant> allTenants();

}
