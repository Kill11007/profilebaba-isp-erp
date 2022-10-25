package com.knackitsolutions.profilebaba.isperp.repository.tenant;

import com.knackitsolutions.profilebaba.isperp.entity.tenant.BalanceSheet;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.BalanceSheet.TransactionType;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceSheetRepository extends PagingAndSortingRepository<BalanceSheet, Long> {

  List<BalanceSheet> findTop1ByCustomerId(Long customerId, Pageable pageable);

  BalanceSheet findByTransactionIdAndTransactionType(Long transactionId,
      TransactionType transactionType);

  Page<BalanceSheet> findByCustomerId(Long customerId, Pageable pageable);

}
