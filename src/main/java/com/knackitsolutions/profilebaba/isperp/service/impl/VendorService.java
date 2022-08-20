package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.controller.VendorController.SignUpRequest;
import com.knackitsolutions.profilebaba.isperp.dto.GenericResponse;
import com.knackitsolutions.profilebaba.isperp.dto.VendorDTO;
import com.knackitsolutions.profilebaba.isperp.entity.Vendor;
import com.knackitsolutions.profilebaba.isperp.exception.BusinessNameNotUniqueException;
import com.knackitsolutions.profilebaba.isperp.exception.InvalidOTPException;
import com.knackitsolutions.profilebaba.isperp.exception.VendorNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.OTPNotSentException;
import com.knackitsolutions.profilebaba.isperp.exception.PhoneNumberAlreadyExistException;
import com.knackitsolutions.profilebaba.isperp.helper.VendorUploadHelper;
import com.knackitsolutions.profilebaba.isperp.repository.VendorRepository;
import com.knackitsolutions.profilebaba.isperp.service.OTPService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class VendorService {

  private final VendorRepository vendorRepository;
  private final OTPService otpService;
  private final PasswordEncoder passwordEncoder;
  private final VendorUploadHelper vendorUploadHelper;

  public GenericResponse sendOTP(String phoneNumber) throws OTPNotSentException {
    Boolean aBoolean = vendorRepository.existsByPhoneNumber(phoneNumber);
    log.info("Number Exists: " + aBoolean);
    String exists =
        aBoolean ? "Phone number already exists."
            : "Phone number is new: " + phoneNumber;
    String otp;
    if (aBoolean) {
      otp = otpService.sendOTP(phoneNumber);
    }else{
      throw new OTPNotSentException(exists);
    }
    return new GenericResponse(otp, exists);
  }

  public GenericResponse validateOTP(String phoneNumber, String otp)
      throws InvalidOTPException, VendorNotFoundException {
    otpService.validateOTP(phoneNumber, otp);
    Vendor vendor = vendorRepository.findByPhoneNumber(phoneNumber)
        .orElseThrow(VendorNotFoundException::new);
    vendorRepository.validateVendor(true, vendor.getId());
    return new GenericResponse("OTP Validated");
  }

  public GenericResponse signUp(SignUpRequest signUpRequest)
      throws BusinessNameNotUniqueException, OTPNotSentException, PhoneNumberAlreadyExistException {
    Vendor vendor = new Vendor();
    vendor.setPhoneNumber(signUpRequest.getPhoneNumber());
    if (vendorRepository.existsByBusinessName(signUpRequest.getBusinessName())) {
      throw new BusinessNameNotUniqueException();
    }
    if (vendorRepository.existsByPhoneNumber(signUpRequest.getPhoneNumber())) {
      throw new PhoneNumberAlreadyExistException();
    }
    vendor.setBusinessName(signUpRequest.getBusinessName());
    vendor.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
    Vendor save = vendorRepository.save(vendor);
    otpService.sendOTP(save.getPhoneNumber());
//    vendorUploadHelper.createVendorDirectory(vendor); TODO
    return new GenericResponse(save.getId().toString(), "Vendor is saved.");
  }

  public VendorDTO findById(Long vendorId) throws VendorNotFoundException{
    return new VendorDTO(
        vendorRepository.findById(vendorId).orElseThrow(VendorNotFoundException::new));
  }

  public VendorDTO profile(Authentication authentication) {
    Vendor vendor = (Vendor) authentication.getPrincipal();
    return new VendorDTO(vendor);
  }

  public void resetPassword(String phoneNumber, String otp, String password)
      throws InvalidOTPException, VendorNotFoundException {
    Vendor vendor = vendorRepository.findByPhoneNumber(phoneNumber).orElseThrow(
        () -> new VendorNotFoundException("Vendor not found by phone number: " + phoneNumber));
    validateOTP(vendor.getPhoneNumber(), otp);
    updatePassword(vendor, phoneNumber, password);
  }

  public void changePassword(String phoneNumber, String password)
      throws InvalidOTPException, VendorNotFoundException {
    Vendor vendor = vendorRepository.findByPhoneNumber(phoneNumber).orElseThrow(
        () -> new VendorNotFoundException("Vendor not found by phone number: " + phoneNumber));
    updatePassword(vendor, phoneNumber, password);
  }

  private void updatePassword(Vendor vendor, String phoneNumber, String password) {
    vendor.setPassword(passwordEncoder.encode(password));
    vendorRepository.save(vendor);
  }
}

