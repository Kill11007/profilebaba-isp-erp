package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.controller.VendorController.SignUpRequest;
import com.knackitsolutions.profilebaba.isperp.dto.GenericResponse;
import com.knackitsolutions.profilebaba.isperp.entity.Vendor;
import com.knackitsolutions.profilebaba.isperp.exception.BusinessNameNotUniqueException;
import com.knackitsolutions.profilebaba.isperp.exception.InvalidOTPException;
import com.knackitsolutions.profilebaba.isperp.exception.VendorNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.OTPNotSentException;
import com.knackitsolutions.profilebaba.isperp.exception.PhoneNumberAlreadyExistException;
import com.knackitsolutions.profilebaba.isperp.helper.VendorUploadHelper;
import com.knackitsolutions.profilebaba.isperp.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VendorService {

  private final VendorRepository vendorRepository;
  private final OTPService otpService;
  private final PasswordEncoder passwordEncoder;
  private final VendorUploadHelper vendorUploadHelper;

  public GenericResponse sendOTP(String phoneNumber) throws OTPNotSentException {
    String exists =
        vendorRepository.existsByPhoneNumber(phoneNumber) ? "Phone number already exists."
            : "Phone number is new.";
    String otp = otpService.sendOTP(phoneNumber);
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
    vendorUploadHelper.createVendorDirectory(vendor);
    return new GenericResponse(save.getId().toString(), "Vendor is saved.");
  }

}

