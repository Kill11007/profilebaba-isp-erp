package com.knackitsolutions.profilebaba.isperp.entity.tenant;

import com.knackitsolutions.profilebaba.isperp.dto.PlanDTO;
import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "plans")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Plan {

  public Plan(PlanDTO dto) {
    this.setId(dto.getId());
    this.setActive(dto.getActive());
    this.setDiscount(dto.getDiscount());
    this.setAdditionalCharge(dto.getAdditionalCharge());
    this.setGstPercent(dto.getGstPercent());
    this.setHsnCode(dto.getHsnCode());
    this.setProductCode(dto.getProductCode());
    this.setName(dto.getName());
    this.setPrice(dto.getPrice());
  }

  public Plan(Plan plan) {
    this.setId(plan.getId());
    this.setActive(plan.getActive());
    this.setDiscount(plan.getDiscount());
    this.setAdditionalCharge(plan.getAdditionalCharge());
    this.setGstPercent(plan.getGstPercent());
    this.setHsnCode(plan.getHsnCode());
    this.setProductCode(plan.getProductCode());
    this.setName(plan.getName());
    this.setPrice(plan.getPrice());
  }

  public void updatePlan(PlanDTO plan) {
    this.setActive(plan.getActive());
    this.setDiscount(plan.getDiscount());
    this.setAdditionalCharge(plan.getAdditionalCharge());
    this.setGstPercent(plan.getGstPercent());
    this.setHsnCode(plan.getHsnCode());
    this.setProductCode(plan.getProductCode());
    this.setName(plan.getName());
    this.setPrice(plan.getPrice());
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  private String productCode;
  private String hsnCode;

  private BigDecimal gstPercent;

  @Column(nullable = false)
  private BigDecimal price;

  private BigDecimal discount;
  private BigDecimal additionalCharge;

  private Boolean active;

  @OneToMany(mappedBy = "plan")
  private Set<Subscription> subscriptions;
}
