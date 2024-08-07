package com.knackitsolutions.profilebaba.isperp.entity.main;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "plan_permissions")
@Getter
@Setter
@NoArgsConstructor
public class IspPlanPermission {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "plan_id")
  @JsonBackReference
  private IspPlan ispPlan;

  @ManyToOne
  @JoinColumn(name = "permission_id")
  @JsonBackReference
  private Permission permission;
  public IspPlanPermission(IspPlan plan, Permission permission) {
    this.permission = permission;
    this.ispPlan = plan;
  }
}
