package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.dto.GenericResponse;
import com.knackitsolutions.profilebaba.isperp.dto.VendorDTO;
import com.knackitsolutions.profilebaba.isperp.exception.BusinessNameNotUniqueException;
import com.knackitsolutions.profilebaba.isperp.exception.InvalidOTPException;
import com.knackitsolutions.profilebaba.isperp.exception.VendorNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.OTPNotSentException;
import com.knackitsolutions.profilebaba.isperp.exception.PhoneNumberAlreadyExistException;
import com.knackitsolutions.profilebaba.isperp.service.AuthenticationFacade;
import com.knackitsolutions.profilebaba.isperp.service.VendorService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vendors")
@CrossOrigin("*")
@RequiredArgsConstructor
public class VendorController {

  private final VendorService vendorService;
  private final AuthenticationFacade authenticationFacade;

  @PostMapping("/sent-otp")
  public ResponseEntity<GenericResponse> sendOTP(@RequestBody String phoneNumber)
      throws OTPNotSentException {
    return ResponseEntity.ok().body(vendorService.sendOTP(phoneNumber));
  }

  @PostMapping("/validate-otp")
  public ResponseEntity<GenericResponse> validateOTP(@RequestBody ValidateOTPRequest request)
      throws InvalidOTPException, VendorNotFoundException {
    return ResponseEntity.ok()
        .body(vendorService.validateOTP(request.getPhoneNumber(), request.getOtp()));
  }

  @PostMapping("/signup")
  public ResponseEntity<GenericResponse> signUp(@RequestBody SignUpRequest signUpRequest)
      throws BusinessNameNotUniqueException, OTPNotSentException, PhoneNumberAlreadyExistException {
    return ResponseEntity.ok(vendorService.signUp(signUpRequest));
  }

  @GetMapping("/{vendor-id}")
  public ResponseEntity<VendorDTO> get(@PathVariable("vendor-id") Long vendorId)
      throws VendorNotFoundException {
    return ResponseEntity.ok(vendorService.findById(vendorId));
  }

  @GetMapping("/profile")
  public ResponseEntity<VendorDTO> profile() {
    return ResponseEntity.ok(vendorService.profile(authenticationFacade.getAuthentication()));
  }

  @Data
  public static class ValidateOTPRequest {
    private String phoneNumber;
    private String otp;
  }

  @Data
  public static class SignUpRequest {
    private String phoneNumber;
    private String businessName;
    private String password;
  }

  @Data
  public static class LoginRequest {
    private String phoneNumber;
    private String password;
    private String rememberMe;
  }

}

