package com.knackitsolutions.profilebaba.isperp.entity.converters;

import com.knackitsolutions.profilebaba.isperp.enums.ComplaintStatus;
import java.util.Optional;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ComplaintStatusConverter implements AttributeConverter<ComplaintStatus, String> {

  @Override
  public String convertToDatabaseColumn(ComplaintStatus attribute) {
    return Optional.ofNullable(attribute).map(ComplaintStatus::getStatus)
        .orElse(ComplaintStatus.NA.getStatus());
  }

  @Override
  public ComplaintStatus convertToEntityAttribute(String dbData) {
    return ComplaintStatus.of(dbData);
  }
}
