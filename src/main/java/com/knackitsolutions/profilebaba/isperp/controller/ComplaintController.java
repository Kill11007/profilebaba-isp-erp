package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.dto.ComplaintDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Complaint;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.EmployeeNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.ComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/complaints")
public class ComplaintController {

  private final ComplaintService complaintService;

  @PostMapping
  public ResponseEntity<Void> newComplaint(@RequestBody ComplaintDTO dto,
      UriComponentsBuilder uriComponentsBuilder)
      throws CustomerNotFoundException, EmployeeNotFoundException, UserNotFoundException {
    Complaint complaint = complaintService.newComplaint(dto);
    return ResponseEntity.created(
            uriComponentsBuilder.path("/complaints/{id}").buildAndExpand(complaint.getId()).toUri())
        .build();
  }

  @GetMapping
  public ResponseEntity<Page<ComplaintDTO>> all(@RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "1000") Integer size) {
    return ResponseEntity.ok(complaintService.allDTO(PageRequest.of(page, size)));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ComplaintDTO> one(@PathVariable Long id)
      throws UserNotFoundException, EmployeeNotFoundException, CustomerNotFoundException {
    return ResponseEntity.ok(complaintService.oneDTO(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    complaintService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody ComplaintDTO dto)
      throws EmployeeNotFoundException, UserNotFoundException {
    complaintService.update(id, dto);
    return ResponseEntity.noContent().build();
  }

  /*@PatchMapping("/{id}/update")
  public ResponseEntity<Void> update(@PathVariable Long id, @RequestParam String action,
      @RequestParam String value)
      throws EmployeeNotFoundException, UserNotFoundException {
    if ("status".equalsIgnoreCase(action)) {
      complaintService.updateStatus(id, value);
    } else if ("employee".equalsIgnoreCase(action)) {
      complaintService.assignAgent(id, Long.parseLong(value));
    } else if("remark".equalsIgnoreCase(action)){
      complaintService.updateEmployeeRemark(id, value);
    }
    return ResponseEntity.noContent().build();
  }*/

  @PatchMapping("/{id}/update")
  public ResponseEntity<Void> update(@PathVariable Long id, @RequestParam Map<String, String> params)
      throws EmployeeNotFoundException, UserNotFoundException {
    String status = params.get("status");
    String employee = params.get("employee");
    String remarks = params.get("remark");
    if(StringUtils.hasText(status)){
      complaintService.updateStatus(id, status);
    }
    if (StringUtils.hasText(employee)) {
      complaintService.assignAgent(id, Long.parseLong(employee));
    }
    if(StringUtils.hasText(remarks)){
      complaintService.updateEmployeeRemark(id, remarks);
    }
    return ResponseEntity.noContent().build();
  }
}
