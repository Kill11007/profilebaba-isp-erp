package com.knackitsolutions.profilebaba.isperp.entity.converters;

import com.knackitsolutions.profilebaba.isperp.enums.BillType;
import com.knackitsolutions.profilebaba.isperp.enums.BillType.BillTypeNotFoundException;
import java.util.Optional;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BillTypeConverter implements AttributeConverter<BillType, String> {

  @Override
  public String convertToDatabaseColumn(BillType attribute) {
    return Optional.ofNullable(attribute)
        .orElseThrow(() -> new BillTypeNotFoundException("Bill type cannot be null.")).getType();
  }

  @Override
  public BillType convertToEntityAttribute(String dbData) {
    return BillType.of(dbData);
  }
}
