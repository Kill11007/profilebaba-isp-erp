package com.knackitsolutions.profilebaba.isperp.entity.tenant;

import com.knackitsolutions.profilebaba.isperp.dto.EmployeeDTO;
import com.knackitsolutions.profilebaba.isperp.dto.NewEmployeeRequest;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

@Entity
@Table(name = "employees")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String name;
  private String address;
  private String email;
  private Long userId;

  @ManyToMany
  @JoinTable(name = "employee_service_areas", joinColumns = @JoinColumn(name = "employee_id"),
      inverseJoinColumns = @JoinColumn(name = "service_area_id"))
  @Exclude
  private Set<ServiceArea> serviceAreas;

  @OneToMany(mappedBy = "employee")
  @Exclude
  private Set<Payment> payments;


  public Employee(NewEmployeeRequest request) {
    setName(request.getName());
    setAddress(request.getAddress());
    setEmail(request.getEmail());
  }

  public void update(EmployeeDTO dto) {
    setName(dto.getName());
    setAddress(dto.getAddress());
    setEmail(dto.getEmail());
  }
}
