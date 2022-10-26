package com.knackitsolutions.profilebaba.isperp.repository.tenant;

import com.knackitsolutions.profilebaba.isperp.entity.tenant.CustomerFollowUp;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerFollowUpRepository extends JpaRepository<CustomerFollowUp, Long> {

  Optional<CustomerFollowUp> findByCustomer_Id(Long customerId);

}
