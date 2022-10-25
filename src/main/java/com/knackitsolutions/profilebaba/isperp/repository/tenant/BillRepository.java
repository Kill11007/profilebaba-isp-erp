package com.knackitsolutions.profilebaba.isperp.repository.tenant;

import com.knackitsolutions.profilebaba.isperp.entity.tenant.BalanceSheet;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Bill;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends PagingAndSortingRepository<Bill, Long> {
  List<Bill> findTop1ByCustomerId(Long customerId, Pageable pageable);

}
