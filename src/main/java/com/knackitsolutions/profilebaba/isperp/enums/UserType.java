package com.knackitsolutions.profilebaba.isperp.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.knackitsolutions.profilebaba.isperp.entity.main.User;
import java.util.Arrays;
import java.util.function.Supplier;
import lombok.Getter;

public enum UserType {
  ADMIN("ADMIN"),
  EMPLOYEE("EMPLOYEE"),
  ISP("ISP");

  @Getter
  @JsonValue
  private final String userType;

  UserType(String userType) {
    this.userType = userType;
  }

  public static UserType of(String userType) {
    return Arrays.stream(UserType.values())
        .filter(ut -> ut.getUserType().equalsIgnoreCase(userType))
        .findFirst().orElseThrow(() -> new UserTypeNotFoundException(userType));
  }

  public static class UserTypeNotFoundException extends RuntimeException{
    private static final Supplier<Throwable> defaultCause = () -> new Throwable(
        "User type is not supported.");
    public UserTypeNotFoundException() {
      super("user type does not exists. Correct values(ADMIN, ISP, EMPLOYEE).",
          defaultCause.get());
    }

    public UserTypeNotFoundException(String userType) {
      super("user type " + userType + " does not exists. Correct values(ADMIN, ISP, EMPLOYEE).",
          defaultCause.get());
    }

    public UserTypeNotFoundException(String message, Throwable cause) {
      super(message, cause);
    }
  }
}
