package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.dto.CustomerDTO;
import com.knackitsolutions.profilebaba.isperp.dto.CustomerQuery;
import com.knackitsolutions.profilebaba.isperp.dto.EmployeeDTO;
import com.knackitsolutions.profilebaba.isperp.dto.EmployeeQuery;
import com.knackitsolutions.profilebaba.isperp.dto.IspDTO;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerAlreadyExistsException;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.VendorNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.CustomerService;
import com.knackitsolutions.profilebaba.isperp.service.IspService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/isps")
@CrossOrigin("*")
@RequiredArgsConstructor
@Log4j2
public class IspController {

  private final IspService ispService;
  private final CustomerService customerService;

  @GetMapping
  public ResponseEntity<Page<IspDTO>> getAll()
      throws UserNotFoundException {
    return ResponseEntity.ok(ispService.getISPs());
  }

  @GetMapping("/{vendor-id}/employees")
  public ResponseEntity<Page<EmployeeDTO>> getEmployees(@PathVariable("vendor-id") Long ispId, @RequestBody(required = false)
      EmployeeQuery employeeQuery)
      throws VendorNotFoundException, UserNotFoundException {
    return ResponseEntity.ok(ispService.getEmployees(ispId, employeeQuery));
  }

  @GetMapping("/{vendor-id}/customers")
  public ResponseEntity<Page<CustomerDTO>> getCustomers(@PathVariable("vendor-id") Long ispId,
      @RequestBody(required = false) CustomerQuery customerQuery)
      throws VendorNotFoundException, UserNotFoundException {
    return ResponseEntity.ok(ispService.getCustomers(ispId, customerQuery));
  }

  @PostMapping("/{vendor-id}/upload/excel")
  public ResponseEntity<?> excelImportCustomers(@PathVariable("vendor-id") Long vendorId,
      @RequestParam("file") MultipartFile multipartFile,
      UriComponentsBuilder uriBuilder) throws CustomerAlreadyExistsException {
    customerService.importCustomers(multipartFile);
    return ResponseEntity.created(uriBuilder.path("{vendor-id}/customers").build(vendorId)).build();
  }

}
