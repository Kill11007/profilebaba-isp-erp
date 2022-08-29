package com.knackitsolutions.profilebaba.isperp.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component("currentTenantIdentifierResolver")
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

  @Override
  public String resolveCurrentTenantIdentifier() {
    String tenantId = TenantContext.getTenantId();
    if (StringUtils.hasText(tenantId)) {
      return tenantId;
    } else {
      // Allow bootstrapping the EntityManagerFactory, in which case no tenant is needed
      return "BOOTSTRAP";
    }
  }

  @Override
  public boolean validateExistingCurrentSessions() {
    return false;
  }
}
