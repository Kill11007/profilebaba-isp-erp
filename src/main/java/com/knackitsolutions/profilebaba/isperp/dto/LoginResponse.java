package com.knackitsolutions.profilebaba.isperp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.knackitsolutions.profilebaba.isperp.enums.UserType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@Builder
public class LoginResponse {
  private UserInfo user;
  private VendorDTO vendor;
  private UserType userType;
  private List<MenuItem> menuItem;
  private JwtResponse token;
}
