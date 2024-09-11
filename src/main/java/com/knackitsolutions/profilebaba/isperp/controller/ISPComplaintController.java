package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.dto.ISPComplaintDTO;
import com.knackitsolutions.profilebaba.isperp.entity.main.ISPComplaint;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.EmployeeNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.ISPComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/complaints")
public class ISPComplaintController {
        private final ISPComplaintService complaintService;

    @PostMapping
    public ResponseEntity<Void> newComplaint(@RequestBody ISPComplaintDTO dto,
                                             UriComponentsBuilder uriComponentsBuilder)
            throws CustomerNotFoundException, EmployeeNotFoundException, UserNotFoundException {
        ISPComplaint complaint = complaintService.newISPComplaint(dto);
        return ResponseEntity.created(
                        uriComponentsBuilder.path("/complaints/{id}").buildAndExpand(complaint.getId()).toUri())
                .build();
    }

    @GetMapping
    public ResponseEntity<Page<ISPComplaintDTO>> all(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                  @RequestParam(required = false, defaultValue = "1000") Integer size) {
        return ResponseEntity.ok(complaintService.allDTO(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ISPComplaintDTO> one(@PathVariable Long id)
            throws UserNotFoundException, EmployeeNotFoundException, CustomerNotFoundException {
        return ResponseEntity.ok(complaintService.oneDTO(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        complaintService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody ISPComplaintDTO dto)
            throws EmployeeNotFoundException, UserNotFoundException {
        complaintService.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestParam Map<String, String> params)
            throws EmployeeNotFoundException, UserNotFoundException {
        String status = params.get("status");
        String employee = params.get("employee");
        String remarks = params.get("remark");
        if(StringUtils.hasText(status)){
            complaintService.updateStatus(id, status);
        }
        if(StringUtils.hasText(remarks)){
            complaintService.updateComments(id, remarks);
        }
        return ResponseEntity.noContent().build();
    }
}
