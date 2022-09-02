package com.knackitsolutions.profilebaba.isperp.repository.tenant;

import com.knackitsolutions.profilebaba.isperp.entity.tenant.Bill;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends PagingAndSortingRepository<Bill, Long> {

}
