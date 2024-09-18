package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.dto.CustomerCareContactDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.CustomerCareContact;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.CustomerCareContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/customer-care-contacts")
@CrossOrigin("*")
@RequiredArgsConstructor
@Log4j2
public class CustomerCareContactController {

  private final CustomerCareContactRepository repository;

  @PostMapping
  public ResponseEntity<Void> createNewContact(@RequestBody CustomerCareContactDTO dto, UriComponentsBuilder uriComponentsBuilder) {
    CustomerCareContact customerCareContact = new CustomerCareContact(dto);
    CustomerCareContact entity = repository.save(customerCareContact);
    return ResponseEntity.created(
            uriComponentsBuilder.path("/customer-care-contacts/{id}").buildAndExpand(entity.getId()).toUri())
        .build();
  }

  @GetMapping
  public ResponseEntity<Page<CustomerCareContactDTO>> all(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                          @RequestParam(required = false, defaultValue = "1000") Integer size){
    return ResponseEntity.ok(repository.findAll(PageRequest.of(page, size)).map(CustomerCareContactDTO::new));
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerCareContactDTO> one(@PathVariable final Long id) {
    return ResponseEntity.ok(new CustomerCareContactDTO(repository.findById(id).orElseThrow()));
  }

}
