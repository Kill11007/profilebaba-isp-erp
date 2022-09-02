package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.dto.SubscriptionDTO;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.SubscriptionNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.impl.AuthenticationFacade;
import com.knackitsolutions.profilebaba.isperp.service.SubscriptionService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@CrossOrigin
public class SubscriptionController {

  private final AuthenticationFacade authenticationFacade;

  private final SubscriptionService subscriptionService;

  @GetMapping("/subscriptions")
  public ResponseEntity<List<SubscriptionDTO>> all() {
    return ResponseEntity.ok(subscriptionService.getAll());
  }

  @GetMapping("/customers/{customer-id}/subscriptions")
  public ResponseEntity<List<SubscriptionDTO>> allUserSubscription(
      @PathVariable("customer-id") Long customerId)
      throws CustomerNotFoundException {
    return ResponseEntity.ok(
        subscriptionService.getCustomerSubscription(customerId));
  }

  @GetMapping("/subscriptions/{subscription-id}")
  public ResponseEntity<SubscriptionDTO> one(@PathVariable("subscription-id") Long subscriptionId)
      throws SubscriptionNotFoundException {
    return ResponseEntity.ok(subscriptionService.getOne(subscriptionId));
  }

  @PutMapping("/subscriptions/{subscription-id}")
  public ResponseEntity<Void> update(@PathVariable("subscription-id") Long subscriptionId,
      @RequestBody SubscriptionDTO subscriptionDTO) throws SubscriptionNotFoundException {
    subscriptionService.updateSubscription(subscriptionId, subscriptionDTO);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/subscriptions/{subscription-id}")
  public ResponseEntity<Void> delete(@PathVariable("subscription-id") Long subscriptionId)
      throws SubscriptionNotFoundException {
    subscriptionService.deleteSubscription(subscriptionId);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/subscriptions/{subscription-id}")
  public ResponseEntity<Void> activate(@PathVariable("subscription-id") Long subscriptionId)
      throws SubscriptionNotFoundException {
    subscriptionService.activate(subscriptionId);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/customers/{customer-id}/subscriptions")
  public ResponseEntity<Void> addUserSubscription(@PathVariable("customer-id") Long customerId,
      @Valid @RequestBody @NotNull SubscriptionDTO subscriptionDTO) throws CustomerNotFoundException {
    subscriptionService.addSubscription(customerId, subscriptionDTO);
    return ResponseEntity.noContent().build();
  }

}
