package com.knackitsolutions.profilebaba.isperp.dto;

import lombok.Data;

@Data
public class GenericResponse {
  String data;
  String message;

  public GenericResponse() {
    data = "";
    message = "";
  }

  public GenericResponse(String message) {
    this();
    this.message = message;
  }

  public GenericResponse(String data, String message) {
    this();
    this.data = data;
    this.message = message;
  }
  public GenericResponse(Long id, String message) {
    this();
    this.data = id.toString();
    this.message = message;
  }
}
