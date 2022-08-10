package com.knackitsolutions.profilebaba.isperp.repository;

import com.knackitsolutions.profilebaba.isperp.entity.Subscription;
import com.knackitsolutions.profilebaba.isperp.entity.Subscription.SubscriptionStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

  List<Subscription> findAllByCustomerId(Long customerId);

  @Modifying
  @Query("update Subscription s set s.status = :status where s.id = :id")
  @Transactional
  void activateSubscription(SubscriptionStatus status, Long id);

}
