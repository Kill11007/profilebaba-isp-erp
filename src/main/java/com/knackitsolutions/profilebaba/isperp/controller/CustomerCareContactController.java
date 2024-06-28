package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.dto.CustomerCareContactDTO;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.CustomerCareContact;
import com.knackitsolutions.profilebaba.isperp.repository.tenant.CustomerCareContactRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
  public ResponseEntity<List<CustomerCareContactDTO>> all(){
    return ResponseEntity.ok(repository.findAll().stream().map(CustomerCareContactDTO::new)
        .collect(Collectors.toList()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerCareContactDTO> one(@PathVariable final Long id) {
    return ResponseEntity.ok(new CustomerCareContactDTO(repository.findById(id).orElseThrow()));
  }

}
