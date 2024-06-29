package com.knackitsolutions.profilebaba.isperp.entity.main;

import com.knackitsolutions.profilebaba.isperp.dto.IspPlanDTO;
import com.knackitsolutions.profilebaba.isperp.enums.PlanType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "plans")
@Data
@NoArgsConstructor
public class IspPlan {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String planDescription;
  private PlanType planType;
  private BigDecimal rate;
  private LocalDateTime createdDateTime;
  @Column(name = "updated_time")
  private LocalDateTime updateDateTime;
  @OneToMany(mappedBy = "ispPlan")
  private List<Vendor> vendor;
  @OneToMany(mappedBy = "ispPlan")
  private Set<IspPlanPermission> ispPlanPermissions = new HashSet<>();

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
