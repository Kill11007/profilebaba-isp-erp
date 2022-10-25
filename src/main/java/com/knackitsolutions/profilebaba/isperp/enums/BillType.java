package com.knackitsolutions.profilebaba.isperp.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import java.util.function.Supplier;
import lombok.Getter;

public enum BillType {
  PRE_PAID("PRE_PAID"),
  POST_PAID("POST_PAID");
  @Getter
  @JsonValue
  private String type;

  BillType(String type) {
    this.type = type;
  }

  public static BillType of(String type) {
    return Arrays.stream(BillType.values())
        .filter(billType -> billType.getType().equalsIgnoreCase(type)).findFirst()
        .orElseThrow(BillTypeNotFoundException::new);
  }

  public static class BillTypeNotFoundException extends RuntimeException {

    private static final Supplier<Throwable> defaultCause = () -> new Throwable(
        "Bill type is not supported");

    public BillTypeNotFoundException() {
      super("Correct values for bill type 'PRE_PAID', 'POST_POST'",
          defaultCause.get());
    }

    public BillTypeNotFoundException(String message) {
      super(message, defaultCause.get());
    }

    public BillTypeNotFoundException(String message, Throwable cause) {
      super(message, cause);
    }
  }

}
