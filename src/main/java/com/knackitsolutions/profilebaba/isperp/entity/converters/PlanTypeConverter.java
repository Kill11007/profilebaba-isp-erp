package com.knackitsolutions.profilebaba.isperp.entity.converters;

import com.knackitsolutions.profilebaba.isperp.enums.PlanType;
import com.knackitsolutions.profilebaba.isperp.enums.PlanType.PlanTypeNotFoundException;
import java.util.Optional;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PlanTypeConverter implements AttributeConverter<PlanType, String> {

  @Override
  public String convertToDatabaseColumn(PlanType attribute) {
    return Optional.ofNullable(attribute).map(PlanType::getType)
        .orElseThrow(() -> new PlanTypeNotFoundException("Plan type is null"));
  }

  @Override
  public PlanType convertToEntityAttribute(String dbData) {
    return PlanType.of(dbData);
  }
}
