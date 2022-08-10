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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vendors")
@CrossOrigin
@RequiredArgsConstructor
@Log4j2
public class VendorController {

  private final VendorService vendorService;
  private final AuthenticationFacade authenticationFacade;

  @PostMapping("/send-otp")
  public ResponseEntity<GenericResponse> sendOTP(@RequestBody SendOTPRequest request)
      throws OTPNotSentException {
    return ResponseEntity.ok().body(vendorService.sendOTP(request.getPhoneNumber()));
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

  @PutMapping("/change-password")
  public ResponseEntity<Void> updatePassword(@RequestBody VendorChangePasswordRequest request)
      throws InvalidOTPException, VendorNotFoundException {
    vendorService.updatePassword(request.getPhoneNumber(), request.getOtp(),
        request.getPassword());
    return ResponseEntity.noContent().build();
  }
  @Data
  public static class ValidateOTPRequest {
    private String phoneNumber;
    private String otp;
  }

  @Data
  public static class SendOTPRequest{
    private String phoneNumber;
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

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class VendorChangePasswordRequest {
    private String phoneNumber;
    private String otp;
    private String password;
  }

}

