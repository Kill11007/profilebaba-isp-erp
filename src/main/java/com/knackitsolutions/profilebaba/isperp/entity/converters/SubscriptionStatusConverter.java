package com.knackitsolutions.profilebaba.isperp.entity.converters;

import com.knackitsolutions.profilebaba.isperp.entity.tenant.Subscription.SubscriptionStatus;
import com.knackitsolutions.profilebaba.isperp.exception.SubscriptionNotFoundException;
import java.util.Optional;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SubscriptionStatusConverter implements AttributeConverter<SubscriptionStatus, String> {

  @Override
  public String convertToDatabaseColumn(SubscriptionStatus attribute) {
    return Optional.ofNullable(attribute).map(SubscriptionStatus::getStatus)
        .orElseThrow(() -> new SubscriptionNotFoundException("Subscription status not found."));
  }

  @Override
  public SubscriptionStatus convertToEntityAttribute(String dbData) {
    return SubscriptionStatus.of(dbData);
  }
}
