package com.knackitsolutions.profilebaba.isperp.entity;

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
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
  private String phone;
  private String password;

  @ManyToMany
  @JoinTable(name = "employee_permissions", joinColumns = @JoinColumn(name = "employee_id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id"))
  private Set<Permission> permissions;

  @ManyToMany
  @JoinTable(name = "employee_service_areas", joinColumns = @JoinColumn(name = "employee_id"),
      inverseJoinColumns = @JoinColumn(name = "service_area_id"))
  private Set<ServiceArea> serviceAreas;


  public Employee(NewEmployeeRequest request) {
    setName(request.getName());
    setAddress(request.getAddress());
    setEmail(request.getEmail());
    setPhone(request.getPhone());
    setPassword(request.getPassword());
  }

  public void update(EmployeeDTO dto) {
    setName(dto.getName());
    setAddress(dto.getAddress());
    setEmail(dto.getEmail());
    setPhone(dto.getPhone());
  }
}
