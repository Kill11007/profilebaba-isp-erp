package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.main.Tenant;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TenantDTO {
  private Integer id;
  private String tenantId;
  private String db;
  private String password;
  private String url;

  public TenantDTO(Tenant tenant) {
    this.setTenantId(tenant.getTenantId());
    this.setId(tenant.getId());
    this.setPassword(tenant.getPassword());
    this.setDb(tenant.getDb());
    this.setUrl(tenant.getUrl());
  }
}
