package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.dto.CustomerDTO;
import com.knackitsolutions.profilebaba.isperp.dto.CustomerSummary;
import com.knackitsolutions.profilebaba.isperp.dto.HardwareDetailDTO;
import com.knackitsolutions.profilebaba.isperp.entity.Customer;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerAlreadyExistsException;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.HardwareNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CustomerController {

  private final CustomerService customerService;

  @GetMapping
  public ResponseEntity<Page<CustomerSummary>> getCustomers(
      @RequestParam(required = false) MultiValueMap<String, String> queryParams) {
    return ResponseEntity.ok(customerService.getFilteredCustomer(queryParams));
  }

  @GetMapping("/{customer-id}")
  public ResponseEntity<CustomerDTO> getCustomer(@PathVariable("customer-id") Long customerId)
      throws CustomerNotFoundException {
    return ResponseEntity.ok(customerService.getCustomer(customerId));
  }

  @DeleteMapping("/{customer-id}")
  public ResponseEntity<?> deleteCustomer(@PathVariable("customer-id") Long customerId)
      throws CustomerNotFoundException {
    customerService.deleteCustomer(customerId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping
  public ResponseEntity<?> addCustomer(@RequestBody CustomerDTO customerDTO,
      UriComponentsBuilder uriComponentsBuilder) throws CustomerAlreadyExistsException {
    Customer customer = customerService.addCustomer(customerDTO);
    return ResponseEntity.created(
            uriComponentsBuilder.path("/customers/{id}").buildAndExpand(customer.getId()).toUri())
        .build();
  }

  @PutMapping("/{customer-id}")
  public ResponseEntity<?> updateCustomer(@PathVariable("customer-id") Long customerId,
      @RequestBody CustomerDTO customerDTO) throws CustomerNotFoundException {
    customerService.updateCustomer(customerId, customerDTO);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{customer-id}/{active}")
  public ResponseEntity<?> activateCustomer(@PathVariable("customer-id") Long customerId,
      @PathVariable Boolean active) throws CustomerNotFoundException {
    customerService.activate(customerId, active);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{customer-id}/hardware")
  public ResponseEntity<?> addHardware(@PathVariable("customer-id") Long customerId,
      @RequestBody HardwareDetailDTO hardwareDetail) throws CustomerNotFoundException {
    customerService.addHardwareDetail(customerId, hardwareDetail);
    System.out.println();
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/hardware/{hardware-id}")
  public ResponseEntity<?> updateHardware(@PathVariable("hardware-id") Long hardwareId,
      @RequestBody HardwareDetailDTO hardwareDetailDTO) throws HardwareNotFoundException {
    customerService.updateHardwareDetail(hardwareId, hardwareDetailDTO);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/hardware/{hardware-id}")
  public ResponseEntity<?> removeHardware(@PathVariable("hardware-id") Long hardwareId)
      throws HardwareNotFoundException {
    customerService.removeHardwareDetail(hardwareId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/upload/excel")
  public ResponseEntity<?> excelImportCustomers(@RequestParam("file") MultipartFile multipartFile,
      UriComponentsBuilder uriBuilder) throws CustomerAlreadyExistsException {
    customerService.importCustomers(multipartFile);
    return ResponseEntity.created(uriBuilder.path("/customers").build().toUri()).build();
  }

  @GetMapping("/download/excel")
  public ResponseEntity<Resource> downloadExcel(MultiValueMap<String, String> queryParams) {
    Resource resource = customerService.exportCustomers(queryParams);
    return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
  }

}
