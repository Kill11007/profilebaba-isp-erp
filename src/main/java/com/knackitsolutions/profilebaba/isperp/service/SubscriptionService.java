package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.dto.SubscriptionDTO;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.SubscriptionNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SubscriptionService {

  Page<SubscriptionDTO> getAll(Integer page, Integer size);

  SubscriptionDTO getOne(Long subscriptionId) throws SubscriptionNotFoundException;

  Page<SubscriptionDTO> getCustomerSubscription(Long customerId) throws CustomerNotFoundException;

  void updateSubscription(Long subscriptionId, SubscriptionDTO subscriptionDTO)
      throws SubscriptionNotFoundException;

  void activate(Long subscriptionId) throws SubscriptionNotFoundException;

  void deleteSubscription(Long subscriptionId) throws SubscriptionNotFoundException;

  void addSubscription(Long customerId, SubscriptionDTO subscriptionDTO)
      throws CustomerNotFoundException;

  void renewSubscription(Long customerId, SubscriptionDTO subscriptionDTO)
      throws CustomerNotFoundException;
}
