package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.dto.SubscriptionDTO;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.SubscriptionNotFoundException;
import java.util.List;

public interface SubscriptionService {

  List<SubscriptionDTO> getAll();

  SubscriptionDTO getOne(Long subscriptionId) throws SubscriptionNotFoundException;

  List<SubscriptionDTO> getCustomerSubscription(Long customerId) throws CustomerNotFoundException;

  void updateSubscription(Long subscriptionId, SubscriptionDTO subscriptionDTO)
      throws SubscriptionNotFoundException;

  void activate(Long subscriptionId) throws SubscriptionNotFoundException;

  void deleteSubscription(Long subscriptionId) throws SubscriptionNotFoundException;

  void addSubscription(Long customerId, SubscriptionDTO subscriptionDTO)
      throws CustomerNotFoundException;

}
