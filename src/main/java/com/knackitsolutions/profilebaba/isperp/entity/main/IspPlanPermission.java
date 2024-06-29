package com.knackitsolutions.profilebaba.isperp.entity.main;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "plan_permissions")
@Data
@NoArgsConstructor
public class IspPlanPermission {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "plan_id")
  private IspPlan ispPlan;

  @ManyToOne
  @JoinColumn(name = "permission_id")
  private Permission permission;
  public IspPlanPermission(IspPlan plan, Permission permission) {
    this.permission = permission;
    this.ispPlan = plan;
  }
}
