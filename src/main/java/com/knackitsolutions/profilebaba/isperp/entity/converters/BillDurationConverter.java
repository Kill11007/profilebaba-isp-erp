package com.knackitsolutions.profilebaba.isperp.entity.converters;

import com.knackitsolutions.profilebaba.isperp.enums.BillDuration;
import com.knackitsolutions.profilebaba.isperp.enums.BillDuration.BillDurationNotFoundException;
import java.util.Optional;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BillDurationConverter implements AttributeConverter<BillDuration, String> {

  @Override
  public String convertToDatabaseColumn(BillDuration attribute) {
    return Optional.ofNullable(attribute)
        .orElseThrow(() -> new BillDurationNotFoundException("Bill duration cannot be null")).getDuration();
  }

  @Override
  public BillDuration convertToEntityAttribute(String dbData) {
    return BillDuration.of(dbData);
  }

}
