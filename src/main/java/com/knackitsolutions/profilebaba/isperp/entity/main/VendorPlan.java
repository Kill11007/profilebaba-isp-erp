package com.knackitsolutions.profilebaba.isperp.entity.main;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "isp_plans")
@Getter
@Setter
@NoArgsConstructor
public class VendorPlan {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "isp_id")
  @JsonBackReference
  private Vendor vendor;
  @ManyToOne
  @JoinColumn(name = "plan_id")
  @JsonBackReference
  private IspPlan plan;

  private LocalDateTime startDateTime;
  private LocalDateTime endDateTime;
  private LocalDateTime updatedDateTime;

  public VendorPlan(Vendor vendor, IspPlan plan) {
    this.vendor = vendor;
    this.plan = plan;
    this.startDateTime = LocalDateTime.now();
    this.updatedDateTime = LocalDateTime.now();
  }
}
