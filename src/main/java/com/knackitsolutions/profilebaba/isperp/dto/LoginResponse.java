package com.knackitsolutions.profilebaba.isperp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.knackitsolutions.profilebaba.isperp.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class LoginResponse {
  private Long userId;
  private UserType userType;
  private MenuItem menuItem;
  private JwtResponse token;
}
