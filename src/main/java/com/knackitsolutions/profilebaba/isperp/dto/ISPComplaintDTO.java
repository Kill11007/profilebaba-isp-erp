package com.knackitsolutions.profilebaba.isperp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.knackitsolutions.profilebaba.isperp.entity.main.ISPComplaint;
import com.knackitsolutions.profilebaba.isperp.enums.ComplaintStatus;
import com.knackitsolutions.profilebaba.isperp.enums.DateTimeFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
public class ISPComplaintDTO {

    private Long id;
    private String complaintNumber;
    private String message;
    private ComplaintStatus status;
    @JsonFormat(pattern = DateTimeFormat.DATE_TIME, shape = JsonFormat.Shape.STRING)
    private LocalDateTime startDate;
    @JsonFormat(pattern = DateTimeFormat.DATE_TIME, shape = JsonFormat.Shape.STRING)
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
