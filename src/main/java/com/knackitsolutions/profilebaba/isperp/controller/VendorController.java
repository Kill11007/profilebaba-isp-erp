package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.dto.GenericResponse;
import com.knackitsolutions.profilebaba.isperp.dto.VendorDTO;
import com.knackitsolutions.profilebaba.isperp.exception.BusinessNameNotUniqueException;
import com.knackitsolutions.profilebaba.isperp.exception.InvalidOTPException;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.VendorNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.OTPNotSentException;
import com.knackitsolutions.profilebaba.isperp.exception.PhoneNumberAlreadyExistsException;
import com.knackitsolutions.profilebaba.isperp.service.impl.AuthenticationFacade;
import com.knackitsolutions.profilebaba.isperp.service.impl.VendorService;
import javax.validation.constraints.NotNull;
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
      throws InvalidOTPException, UserNotFoundException {
    return ResponseEntity.ok()
        .body(vendorService.validateOTP(request.getPhoneNumber(), request.getOtp()));
  }

  @PostMapping("/signup")
  public ResponseEntity<GenericResponse> signUp(@RequestBody SignUpRequest signUpRequest)
      throws BusinessNameNotUniqueException, OTPNotSentException,
      PhoneNumberAlreadyExistsException, UserNotFoundException, VendorNotFoundException {
    return ResponseEntity.ok(vendorService.signUp(signUpRequest));
  }

  @GetMapping("/{vendor-id}")
  public ResponseEntity<VendorDTO> get(@PathVariable("vendor-id") Long vendorId)
      throws VendorNotFoundException, UserNotFoundException {
    return ResponseEntity.ok(vendorService.findById(vendorId));
  }

  @GetMapping("/profile")
  public ResponseEntity<VendorDTO> profile() throws UserNotFoundException {
    return ResponseEntity.ok(vendorService.profile(authenticationFacade.getAuthentication()));
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
    @NotNull
    private String phoneNumber;
    @NotNull
    private String password;
    private String rememberMe;
    private Long vendorId;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class SetPasswordRequest {
    private String phoneNumber;
    private String otp;
    private String password;
  }

}

