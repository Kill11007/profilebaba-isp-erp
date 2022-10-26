package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.dto.CustomerFollowUpDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.CustomerFollowUp;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.CustomerFollowUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/customer-follow-ups")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CustomerFollowUpController {

  private final CustomerFollowUpService customerFollowUpService;

  @GetMapping
  public ResponseEntity<Object> all(@RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer size) {
    if (page != null && size != null) {
      return ResponseEntity.ok(customerFollowUpService.allDTOs(PageRequest.of(page, size)));
    }else{
      return ResponseEntity.ok(customerFollowUpService.allDTOs());
    }
  }

  @GetMapping("/{id}/customers")
  public ResponseEntity<CustomerFollowUpDTO> getByCustomerId(@PathVariable Long id) {
    return ResponseEntity.ok(customerFollowUpService.oneDTO(id));
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerFollowUpDTO> getById(@PathVariable Long id) {
    return ResponseEntity.ok(customerFollowUpService.oneDTOById(id));
  }

  @PostMapping
  public ResponseEntity<Void> newFollowUp(@RequestBody CustomerFollowUpDTO dto,
      UriComponentsBuilder uriComponentsBuilder) throws CustomerNotFoundException {
    CustomerFollowUp customerFollowUp = customerFollowUpService.newFollowUp(dto);
    return ResponseEntity.created(uriComponentsBuilder.path("/customer-follow-ups/{id}")
        .buildAndExpand(customerFollowUp.getId()).toUri()).build();
  }

  @PutMapping
  public ResponseEntity<Void> updateFollowUp(@RequestBody CustomerFollowUpDTO dto){
    customerFollowUpService.updateFollowUp(dto);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteFollowUp(@PathVariable Long id) {
    customerFollowUpService.deleteFollowUp(id);
    return ResponseEntity.noContent().build();
  }
}
