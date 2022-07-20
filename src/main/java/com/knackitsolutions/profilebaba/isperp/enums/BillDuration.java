package com.knackitsolutions.profilebaba.isperp.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import java.util.function.Supplier;
import lombok.Getter;

public enum BillDuration {
  END_OF_EVERY_MONTH("EOEM"),
  DAYS("DAYS"),
  MONTHS("MONTHS");

  @Getter
  @JsonValue
  private String duration;

  BillDuration(String duration) {
    this.duration = duration;
  }

  public static BillDuration of(String duration) {
    return Arrays.stream(BillDuration.values())
        .filter(billDuration -> billDuration.getDuration().equalsIgnoreCase(duration)).findFirst()
        .orElseThrow(() -> new BillDurationNotFoundException(
            "Bill duration " + duration + " is not supported."));
  }

  public static class BillDurationNotFoundException extends RuntimeException{

    private static final Supplier<Throwable> defaultCause = () -> new Throwable(
        "Bill duration is not supported.");
    public BillDurationNotFoundException() {
      super("Bill duration does not exists. Correct values(EOEM, DAYS, MONTHS).",
          defaultCause.get());
    }

    public BillDurationNotFoundException(String message) {
      super(message, defaultCause.get());
    }

    public BillDurationNotFoundException(String message, Throwable cause) {
      super(message, cause);
    }
  }
}
