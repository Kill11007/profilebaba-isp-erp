package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.dto.CustomerDTO;
import com.knackitsolutions.profilebaba.isperp.dto.SubscriptionDTO;
import com.knackitsolutions.profilebaba.isperp.entity.Customer;
import com.knackitsolutions.profilebaba.isperp.entity.Plan;
import com.knackitsolutions.profilebaba.isperp.entity.Subscription;
import com.knackitsolutions.profilebaba.isperp.entity.Subscription.SubscriptionStatus;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.PlanNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.SubscriptionNotFoundException;
import com.knackitsolutions.profilebaba.isperp.repository.SubscriptionRepository;
import com.knackitsolutions.profilebaba.isperp.service.CustomerService;
import com.knackitsolutions.profilebaba.isperp.service.PlanService;
import com.knackitsolutions.profilebaba.isperp.service.SubscriptionService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

  private final SubscriptionRepository repository;
  private final CustomerService customerService;
  private final PlanService planService;
  @Override
  public List<SubscriptionDTO> getAll() {
    return repository.findAll().stream().map(SubscriptionDTO::new).collect(Collectors.toList());
  }

  @Override
  public SubscriptionDTO getOne(Long subscriptionId) throws SubscriptionNotFoundException {
    Subscription subscription = findSubscription(subscriptionId);
    return new SubscriptionDTO(subscription);
  }

  @Override
  public List<SubscriptionDTO> getCustomerSubscription(Long customerId)
      throws CustomerNotFoundException {
    CustomerDTO customer = customerService.getCustomer(customerId);
    List<Subscription> allByCustomerId = repository.findAllByCustomerId(customer.getId());
    return allByCustomerId.stream().map(SubscriptionDTO::new).collect(Collectors.toList());
  }

  @Override
  public void updateSubscription(Long subscriptionId, SubscriptionDTO subscriptionDTO)
      throws SubscriptionNotFoundException, PlanNotFoundException {
    Subscription subscription = findSubscription(subscriptionId);
    subscription.setPlan(planService.getPlanById(subscriptionDTO.getPlanId()));
    subscription.update(subscriptionDTO);
    repository.save(subscription);
  }

  private Subscription findSubscription(Long id) throws SubscriptionNotFoundException{
    return repository.findById(id).orElseThrow(SubscriptionNotFoundException::new);
  }

  @Override
  public void activate(Long subscriptionId) throws SubscriptionNotFoundException {
    Subscription subscription = findSubscription(subscriptionId);
    repository.activateSubscription(updatedStatus(subscription.getStatus()), subscriptionId);
  }

  private SubscriptionStatus updatedStatus(SubscriptionStatus status) {
    if (status == SubscriptionStatus.ACTIVE) {
      return SubscriptionStatus.IN_ACTIVE;
    }
    return SubscriptionStatus.ACTIVE;
  }

  @Override
  public void deleteSubscription(Long subscriptionId) throws SubscriptionNotFoundException {
    Subscription subscription = findSubscription(subscriptionId);
    repository.delete(subscription);
  }

  @Override
  public void addSubscription(Long customerId, SubscriptionDTO subscriptionDTO)
      throws CustomerNotFoundException, PlanNotFoundException {
    Customer customerById = customerService.getCustomerById(customerId);
    Set<Subscription> subscriptions = customerById.getSubscriptions();
    Plan planById = planService.getPlanById(subscriptionDTO.getPlanId());
    Subscription subscription = new Subscription(subscriptionDTO, customerById);
    subscription.setPlan(planService.getPlanById(subscriptionDTO.getPlanId()));
    Subscription save = repository.save(subscription);
    if (subscriptions == null) {
      subscriptions = new HashSet<>();
    }
    subscriptions.add(save);
    customerById.setSubscriptions(subscriptions);
    customerService.updateCustomer(customerById);
  }
}
