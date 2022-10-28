package com.knackitsolutions.profilebaba.isperp.repository.tenant;

import com.knackitsolutions.profilebaba.isperp.entity.tenant.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

}
