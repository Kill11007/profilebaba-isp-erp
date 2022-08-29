package com.knackitsolutions.profilebaba.isperp.config;

public class TenantContext {

  private static final ThreadLocal<String> context = new InheritableThreadLocal<>();

  public static void clear(){
    context.remove();
  }
  public static String getTenantId() {
    return context.get();
  }

  public static void setTenantId(String tenantId) {
    context.set(tenantId);
  }

}
