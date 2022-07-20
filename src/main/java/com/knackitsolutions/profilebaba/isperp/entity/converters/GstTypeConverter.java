package com.knackitsolutions.profilebaba.isperp.entity.converters;

import com.knackitsolutions.profilebaba.isperp.enums.GstType;
import com.knackitsolutions.profilebaba.isperp.enums.GstType.GstTypeNotFoundException;
import java.util.Optional;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class GstTypeConverter implements AttributeConverter<GstType, String> {

  @Override
  public String convertToDatabaseColumn(GstType attribute) {
    return Optional.ofNullable(attribute)
        .orElseThrow(() -> new GstTypeNotFoundException("Gst type cannot be null")).getType();
  }

  @Override
  public GstType convertToEntityAttribute(String dbData) {
    return GstType.of(dbData);
  }
}
