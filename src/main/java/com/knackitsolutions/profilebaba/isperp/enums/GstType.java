package com.knackitsolutions.profilebaba.isperp.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import java.util.function.Supplier;
import lombok.Getter;

public enum GstType {
  NONE("NA"),
  IGST("IGST"),
  CGST_SGST("CGST_SGST");

  @Getter
  @JsonValue
  private String type;

  GstType(String type) {
    this.type = type;
  }

  public static GstType of(String type) {
    return Arrays.stream(GstType.values())
        .filter(gstType -> gstType.getType().equalsIgnoreCase(type)).findFirst().orElseThrow();
  }

  public static class GstTypeNotFoundException extends RuntimeException {

    private static final Supplier<Throwable> defaultCause = () -> new Throwable("Gst type is not supported.");

    public GstTypeNotFoundException() {
      super("Correct GST Type are 'NA', 'IGST', 'CGST_SGST'", defaultCause.get());
    }

    public GstTypeNotFoundException(String message) {
      super(message, defaultCause.get());
    }

    public GstTypeNotFoundException(String message, Throwable cause) {
      super(message, cause);
    }
  }

}
