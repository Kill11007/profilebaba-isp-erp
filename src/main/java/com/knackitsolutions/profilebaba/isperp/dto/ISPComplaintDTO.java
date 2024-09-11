package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.main.ISPComplaint;
import com.knackitsolutions.profilebaba.isperp.enums.ComplaintStatus;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ISPComplaintDTO {

    private Long id;
    private String complaintNumber;
    private String message;
    private ComplaintStatus status;
    private LocalDateTime startDate;
    private LocalDateTime updatedDate;
    private Long createdByUserId;
    private Long ispId;
    private String comments;
    private String createdByUserName;

    public ISPComplaintDTO(ISPComplaint entity) {
        this.id = entity.getId();
        this.complaintNumber = entity.getComplaintNumber();
        this.comments = entity.getComments();
        this.ispId = entity.getIspId();
        this.createdByUserId = entity.getCreatedByUserId();
        this.message = entity.getMessage();
        this.status = entity.getStatus();
        this.startDate = entity.getStartDate();
        this.updatedDate = entity.getUpdatedDate();
    }
}
