package com.knackitsolutions.profilebaba.isperp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {
  String code;
  String cause;
  String message;
}
