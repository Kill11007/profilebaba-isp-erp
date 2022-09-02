package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.controller.VendorController.SignUpRequest;
import com.knackitsolutions.profilebaba.isperp.dto.GenericResponse;
import com.knackitsolutions.profilebaba.isperp.dto.VendorDTO;
import com.knackitsolutions.profilebaba.isperp.entity.main.Tenant;
import com.knackitsolutions.profilebaba.isperp.entity.main.User;
import com.knackitsolutions.profilebaba.isperp.entity.main.Vendor;
import com.knackitsolutions.profilebaba.isperp.exception.BusinessNameNotUniqueException;
import com.knackitsolutions.profilebaba.isperp.exception.InvalidOTPException;
import com.knackitsolutions.profilebaba.isperp.exception.UserAlreadyExistsException;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.VendorNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.OTPNotSentException;
import com.knackitsolutions.profilebaba.isperp.exception.PhoneNumberAlreadyExistsException;
import com.knackitsolutions.profilebaba.isperp.helper.VendorUploadHelper;
import com.knackitsolutions.profilebaba.isperp.repository.main.VendorRepository;
import com.knackitsolutions.profilebaba.isperp.service.OTPService;
import com.knackitsolutions.profilebaba.isperp.service.TenantManagementService;
import com.knackitsolutions.profilebaba.isperp.service.UserService;
import javax.transaction.Transactional;
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
  private final UserService userService;
  private final TenantManagementService tenantManagementService;

  public GenericResponse sendOTP(String phoneNumber) throws OTPNotSentException {
    Boolean aBoolean = userService.existsByPhoneNumber(phoneNumber);
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
      throws InvalidOTPException, UserNotFoundException {
    otpService.validateOTP(phoneNumber, otp);
    User user = userService.findByPhoneNumber(phoneNumber);
    userService.validateUser(true, user.getId());
    return new GenericResponse("OTP Validated");
  }

  @Transactional
  public GenericResponse signUp(SignUpRequest signUpRequest)
      throws BusinessNameNotUniqueException, OTPNotSentException, PhoneNumberAlreadyExistsException {
    if (userService.existsByPhoneNumber(signUpRequest.getPhoneNumber())) {
      throw new PhoneNumberAlreadyExistsException();
    }
    if (vendorRepository.existsByBusinessName(signUpRequest.getBusinessName())) {
      throw new BusinessNameNotUniqueException();
    }
    Tenant tenant = tenantManagementService.createTenant(signUpRequest.getBusinessName(),
        signUpRequest.getBusinessName(),
        signUpRequest.getPassword());
    User user = userService.save(signUpRequest, tenant);
    Vendor vendor = saveVendor(signUpRequest, user.getId());
    otpService.sendOTP(user.getPhoneNumber());
//    vendorUploadHelper.createVendorDirectory(vendor); TODO
    return new GenericResponse(vendor.getId(), "Vendor is saved.");
  }

  public Vendor saveVendor(SignUpRequest signUpRequest, Long userId) {
    Vendor vendor = new Vendor();
    vendor.setBusinessName(signUpRequest.getBusinessName());
    vendor.setUserId(userId);
    return vendorRepository.save(vendor);
  }

  public VendorDTO findById(Long vendorId) throws VendorNotFoundException, UserNotFoundException {
    Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(VendorNotFoundException::new);
    return new VendorDTO(
        vendor, userService.findById(vendor.getUserId()));
  }

  public VendorDTO profile(Authentication authentication) throws UserNotFoundException {
    Vendor vendor = (Vendor) authentication.getPrincipal();
    User user = userService.findById(vendor.getUserId());
    return new VendorDTO(vendor, user);

  }

  public void resetPassword(String phoneNumber, String otp, String password)
      throws InvalidOTPException, UserNotFoundException {
    User user = userService.findByPhoneNumber(phoneNumber);
//        .orElseThrow(
//        () -> new VendorNotFoundException("Vendor not found by phone number: " + phoneNumber));
    validateOTP(user.getPhoneNumber(), otp);
    updatePassword(user, password);
  }

  public void changePassword(String phoneNumber, String password)
      throws UserNotFoundException {
    User user = userService.findByPhoneNumber(phoneNumber);
//        .orElseThrow(
//        () -> new VendorNotFoundException("Vendor not found by phone number: " + phoneNumber));
    updatePassword(user, password);
  }

  private void updatePassword(User user, String password) {
    user.setPassword(passwordEncoder.encode(password));
    userService.save(user);
  }
}

