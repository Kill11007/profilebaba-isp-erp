package com.knackitsolutions.profilebaba.isperp.repository.tenant;

import com.knackitsolutions.profilebaba.isperp.entity.tenant.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillItemRepository extends JpaRepository<BillItem, Long> {

}
