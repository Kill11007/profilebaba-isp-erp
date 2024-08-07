package com.knackitsolutions.profilebaba.isperp.entity.main;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.knackitsolutions.profilebaba.isperp.dto.IspPlanDTO;
import com.knackitsolutions.profilebaba.isperp.entity.converters.PlanTypeConverter;
import com.knackitsolutions.profilebaba.isperp.enums.PlanType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "plans")
@Getter
@Setter
@NoArgsConstructor
public class IspPlan {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String planDescription;
  @Convert(converter = PlanTypeConverter.class)
  private PlanType planType;
  private BigDecimal rate;
  private LocalDateTime createdDateTime;
  @Column(name = "updated_time")
  private LocalDateTime updateDateTime;
  @OneToMany(mappedBy = "ispPlan", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonBackReference
  private Set<IspPlanPermission> ispPlanPermissions = new HashSet<>();
  @OneToMany(mappedBy = "plan", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<VendorPlan> vendorPlans = new HashSet<>();
  private Boolean isDefault;

  public IspPlan(String name, String planDescription, PlanType planType, BigDecimal rate) {
    this.name = name;
    this.planDescription = planDescription;
    this.planType = planType;
    this.rate = rate;
    this.createdDateTime = LocalDateTime.now();
    this.updateDateTime = LocalDateTime.now();
  }

  public IspPlan(IspPlanDTO dto) {
    setName(dto.getName());
    setPlanDescription(dto.getPlanDescription());
    setRate(dto.getRate());
    setPlanType(dto.getPlanType());
    setCreatedDateTime(LocalDateTime.now());
    setUpdateDateTime(LocalDateTime.now());
  }

  public void update(IspPlanDTO dto) {
    setPlanType(dto.getPlanType());
    setPlanDescription(dto.getPlanDescription());
    setName(dto.getName());
    setRate(dto.getRate());
    setUpdateDateTime(LocalDateTime.now());
  }
}
