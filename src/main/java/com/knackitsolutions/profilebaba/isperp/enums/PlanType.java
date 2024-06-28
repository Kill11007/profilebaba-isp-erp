package com.knackitsolutions.profilebaba.isperp.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.knackitsolutions.profilebaba.isperp.exception.PlanNotFoundException;
import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;

public enum PlanType {
  MONTHLY_FIX("monthly"), YEARLY_FIX("yearly"), CUSTOMER("customer"), FREE("free");
  @Getter
  @JsonValue
  private String type;

  PlanType(String type) {
    this.type = type;
  }

  public static PlanType of(String planType) {
    String type = Optional.ofNullable(planType)
        .orElseThrow(() -> new PlanTypeNotFoundException("Plan type cannot be null"));
    return Arrays.stream(PlanType.values()).filter(t -> t.getType().equalsIgnoreCase(type)).findFirst()
        .orElseThrow(
            PlanNotFoundException::new);
  }

  public static class PlanTypeNotFoundException extends RuntimeException{
    private String message;

    public PlanTypeNotFoundException(String message) {
      super(message);
    }
  }

}
