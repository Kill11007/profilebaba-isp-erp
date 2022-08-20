package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
  private Long id;
  private String name;
  private String address;
  private String email;
  private String phone;

  public EmployeeDTO(Employee entity) {
    setId(entity.getId());
    setName(entity.getName());
    setEmail(entity.getEmail());
    setPhone(entity.getPhone());
    setAddress(entity.getAddress());
  }
}
