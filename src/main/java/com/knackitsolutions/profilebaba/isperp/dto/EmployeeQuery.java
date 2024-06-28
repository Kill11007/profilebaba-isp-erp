package com.knackitsolutions.profilebaba.isperp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeQuery {
  private Long employeeId;
  private String name;
  private String phone;

  public EmployeeSpecification toSpecification() {
    return new EmployeeSpecification(this);
  }
}
