package com.knackitsolutions.profilebaba.isperp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SendOTPResponse {
  private String phoneNumberStatus;
  private String otp;
}

