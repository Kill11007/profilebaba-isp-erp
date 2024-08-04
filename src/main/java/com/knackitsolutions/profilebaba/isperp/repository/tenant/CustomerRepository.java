package com.knackitsolutions.profilebaba.isperp.repository.tenant;

import com.knackitsolutions.profilebaba.isperp.entity.tenant.Customer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>,
    JpaSpecificationExecutor<Customer> {

  boolean existsByPrimaryMobileNo(String mobileNo);

  boolean existsByCustomerCode(String customerCode);

  List<Customer> findByPrimaryMobileNo(String phone);
  List<Customer> findBySecondaryMobileNo(String phone);
  List<Customer> findByEmailLike(String email);
  List<Customer> findByNameLike(String name);

  Optional<Customer> findByUserId(Long userId);

  List<Customer> findByCustomerRoleId(Integer customerRoleId);


}
