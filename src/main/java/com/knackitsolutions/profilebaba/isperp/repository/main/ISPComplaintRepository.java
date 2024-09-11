package com.knackitsolutions.profilebaba.isperp.repository.main;

import com.knackitsolutions.profilebaba.isperp.entity.main.ISPComplaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ISPComplaintRepository extends JpaRepository<ISPComplaint, Long> {
    @Query(value = "SELECT MAX(c.id) FROM ISPComplaint c")
    Long findMaxId();
}
