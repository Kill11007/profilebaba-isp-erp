package com.knackitsolutions.profilebaba.isperp.entity;

import java.util.Optional;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BooleanConverter implements AttributeConverter<Boolean, Integer> {

  @Override
  public Integer convertToDatabaseColumn(Boolean attribute) {
    return Optional.ofNullable(attribute).map(b -> b ? 1 : 0).orElse(0);
  }

  @Override
  public Boolean convertToEntityAttribute(Integer dbData) {
    return Optional.ofNullable(dbData).map(i -> i > 0).orElse(false);
  }
}
