package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.dto.ISPComplaintDTO;
import com.knackitsolutions.profilebaba.isperp.entity.main.ISPComplaint;
import com.knackitsolutions.profilebaba.isperp.entity.main.User;
import com.knackitsolutions.profilebaba.isperp.enums.ComplaintStatus;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.EmployeeNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import com.knackitsolutions.profilebaba.isperp.helper.UserServiceHelper;
import com.knackitsolutions.profilebaba.isperp.repository.main.ISPComplaintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;
@Service
@RequiredArgsConstructor
public class ISPComplaintService {
    private final ISPComplaintRepository repository;
    private final UserServiceHelper userServiceHelper;
    private final IAuthenticationFacade authenticationFacade;

    public Page<ISPComplaint> all(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<ISPComplaintDTO> allDTO(Pageable pageable) {
        return all(pageable).map(ISPComplaintDTO::new);
    }

    public ISPComplaint one(Long id) throws ComplaintService.ComplaintNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new ComplaintService.ComplaintNotFoundException(id));
    }

    public ISPComplaintDTO oneDTO(Long id)
            throws ComplaintService.ComplaintNotFoundException {
        ISPComplaintDTO complaintDTO = new ISPComplaintDTO(one(id));
        complaintDTO.setCreatedByUserName(userServiceHelper.getUserName(
                complaintDTO.getCreatedByUserId()));
        return complaintDTO;
    }

    public ISPComplaint newISPComplaint(ISPComplaint complaint) {
        return repository.save(complaint);
    }

    public ISPComplaint newISPComplaint(ISPComplaintDTO dto)
            throws CustomerNotFoundException, EmployeeNotFoundException, UserNotFoundException {
        ISPComplaint complaint = new ISPComplaint(dto);
        User user = (User)authenticationFacade.getAuthentication().getPrincipal();
        complaint.setCreatedByUserId(user.getId());
        complaint.setComplaintNumber(createComplaintNumber(user.getTenantId()));
        return newISPComplaint(complaint);
    }

    private String createComplaintNumber(String tenantId) {
        Long maxId = repository.findMaxId();
        return firstThreeLetter(tenantId) + "00" + (maxId == null ? 1 : maxId + 1);
    }

    private String firstThreeLetter(String tenantId) {
        String[] split = tenantId.split("T-");
        if (split.length < 2) {
            return "C";
        }
        String name = split[1];
        if (name.length() > 3) {
            return name.substring(0, 3).toUpperCase(Locale.ROOT);
        }
        return name.toLowerCase(Locale.ROOT);
    }

    public void update(Long id, ISPComplaintDTO dto)
            throws EmployeeNotFoundException, UserNotFoundException {
        ISPComplaint complaint = one(id);
        complaint.setStatus(dto.getStatus());
        complaint.setMessage(dto.getMessage());
        complaint.setUpdatedDate(LocalDateTime.now());
        repository.save(complaint);
    }

    public void updateStatus(Long id, String status) {
        ISPComplaint one = one(id);
        one.setStatus(ComplaintStatus.of(status));
        repository.save(one);
    }

    public void updateComments(Long id, String comments) {
        ISPComplaint one = one(id);
        String remark = one.getComments() == null ? "" : one.getComments();
        String updatedRemark = remark.concat("\n").concat(comments);
        one.setComments(updatedRemark);
        repository.save(one);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
