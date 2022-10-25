package com.knackitsolutions.profilebaba.isperp.entity.converters;

import com.knackitsolutions.profilebaba.isperp.entity.tenant.Payment.PaymentMode;
import java.util.Optional;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PaymentModeConverter implements AttributeConverter<PaymentMode, String> {

  @Override
  public String convertToDatabaseColumn(PaymentMode attribute) {
    return Optional.ofNullable(attribute).map(PaymentMode::getPaymentMode).orElse("CASH");
  }

  @Override
  public PaymentMode convertToEntityAttribute(String dbData) {
    return PaymentMode.of(dbData);
  }
}
