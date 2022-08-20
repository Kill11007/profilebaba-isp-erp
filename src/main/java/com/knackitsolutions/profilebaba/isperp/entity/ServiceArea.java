package com.knackitsolutions.profilebaba.isperp.entity;

import com.knackitsolutions.profilebaba.isperp.dto.ServiceAreaDTO;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "service_areas")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceArea {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @ManyToMany(mappedBy = "serviceAreas")
  private Set<Employee> employees;

  public ServiceArea(ServiceAreaDTO dto) {
    this.name = dto.getName();
  }

  public void update(ServiceAreaDTO dto) {
    this.name = dto.getName();
  }
}
