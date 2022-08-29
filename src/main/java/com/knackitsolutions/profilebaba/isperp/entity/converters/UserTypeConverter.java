package com.knackitsolutions.profilebaba.isperp.entity.converters;

import com.knackitsolutions.profilebaba.isperp.enums.UserType;
import com.knackitsolutions.profilebaba.isperp.enums.UserType.UserTypeNotFoundException;
import java.util.Optional;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserTypeConverter implements AttributeConverter<UserType, String> {

  @Override
  public String convertToDatabaseColumn(UserType attribute) {
    return Optional.ofNullable(attribute).map(UserType::getUserType).orElseThrow(
        UserTypeNotFoundException::new);
  }

  @Override
  public UserType convertToEntityAttribute(String dbData) {
    return UserType.of(dbData);
  }
}
