package com.knackitsolutions.profilebaba.isperp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewEmployeeRequest {
  private String name;
  private String address;
  private String email;
  private String phone;
  private String password;
}
