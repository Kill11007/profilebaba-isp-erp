package com.knackitsolutions.profilebaba.isperp.entity.main;

import com.knackitsolutions.profilebaba.isperp.dto.ComplaintDTO;
import com.knackitsolutions.profilebaba.isperp.dto.ISPComplaintDTO;
import com.knackitsolutions.profilebaba.isperp.enums.ComplaintStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "isp_complaints")
@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class ISPComplaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String complaintNumber;
    private String message;
    private ComplaintStatus status;
    private LocalDateTime startDate;
    private LocalDateTime updatedDate;
    private Long createdByUserId;
    private Long ispId;
    private String comments;

    public ISPComplaint(ISPComplaintDTO dto) {
        this.setMessage(dto.getMessage());
        this.setStartDate(LocalDateTime.now());
        this.setStatus(ComplaintStatus.OPEN);
        this.setUpdatedDate(LocalDateTime.now());
        this.setComments(dto.getComments());
    }
}
