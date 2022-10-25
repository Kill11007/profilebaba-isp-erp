package com.knackitsolutions.profilebaba.isperp.entity.converters;

import com.knackitsolutions.profilebaba.isperp.entity.tenant.BalanceSheet.TransactionType;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.BalanceSheet.TransactionTypeNotFoundException;
import java.util.Optional;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TransactionTypeConverter implements AttributeConverter<TransactionType, String> {

  @Override
  public String convertToDatabaseColumn(TransactionType attribute) {
    return Optional.ofNullable(attribute).orElseThrow(() -> new TransactionTypeNotFoundException())
        .getType();
  }

  @Override
  public TransactionType convertToEntityAttribute(String dbData) {
    return TransactionType.of(dbData);
  }
}
