package com.knackitsolutions.profilebaba.isperp.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;

public enum ComplaintStatus {
  OPEN("O"),
  WORKING("W"),
  RESOLVED("R"),
  NA("");

  @Getter
  @JsonValue
  private String status;

  ComplaintStatus(final String status) {
    this.status = status;
  }

  public static ComplaintStatus of(String status) {
    String s = Optional.ofNullable(status).orElse("");
    return Arrays.stream(ComplaintStatus.values())
        .filter(complaintStatus -> complaintStatus.getStatus().equalsIgnoreCase(s)).findFirst()
        .orElseThrow(() -> new ComplainStatusNotFoundException(status));
  }

  public static class ComplainStatusNotFoundException extends RuntimeException {

    public ComplainStatusNotFoundException(String status) {
      super("Given status: " + status + " not found.");
    }
  }

}
