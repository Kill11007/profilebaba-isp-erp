package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.controller.VendorController.SignUpRequest;
import com.knackitsolutions.profilebaba.isperp.dto.GenericResponse;
import com.knackitsolutions.profilebaba.isperp.dto.VendorDTO;
import com.knackitsolutions.profilebaba.isperp.entity.main.Tenant;
import com.knackitsolutions.profilebaba.isperp.entity.main.User;
import com.knackitsolutions.profilebaba.isperp.entity.main.Vendor;
import com.knackitsolutions.profilebaba.isperp.exception.BusinessNameNotUniqueException;
import com.knackitsolutions.profilebaba.isperp.exception.InvalidOTPException;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.VendorNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.OTPNotSentException;
import com.knackitsolutions.profilebaba.isperp.exception.PhoneNumberAlreadyExistsException;
import com.knackitsolutions.profilebaba.isperp.helper.VendorUploadHelper;
import com.knackitsolutions.profilebaba.isperp.repository.main.VendorRepository;
import com.knackitsolutions.profilebaba.isperp.service.IspPlanService;
import com.knackitsolutions.profilebaba.isperp.service.OTPService;
import com.knackitsolutions.profilebaba.isperp.service.TenantManagementService;
import com.knackitsolutions.profilebaba.isperp.service.UserService;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class VendorService {

  private final VendorRepository vendorRepository;
  private final OTPService otpService;
  private final UserService userService;
  private final TenantManagementService tenantManagementService;
  private final IspPlanService ispPlanService;

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
    User user = userService.findOneByPhoneNumber(phoneNumber);
    userService.validateUser(true, user.getId());
    return new GenericResponse("OTP Validated");
  }

  @Transactional
  public GenericResponse signUp(SignUpRequest signUpRequest)
      throws BusinessNameNotUniqueException, OTPNotSentException, PhoneNumberAlreadyExistsException, UserNotFoundException, VendorNotFoundException {
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
//    ispPlanService.activateIspFreePlan(vendor.getId());
    otpService.sendOTP(user.getPhoneNumber());
//    vendorUploadHelper.createVendorDirectory(vendor); TODO
    return new GenericResponse(vendor.getId(), "Vendor is saved.");
  }

  public Vendor saveVendor(SignUpRequest signUpRequest, Long userId) {
    Vendor vendor = new Vendor();
    vendor.setBusinessName(signUpRequest.getBusinessName());
    vendor.setUserId(userId);
    vendor.setPhone(signUpRequest.getPhoneNumber());
    return vendorRepository.save(vendor);
  }

  public VendorDTO findById(Long vendorId) throws VendorNotFoundException, UserNotFoundException {
    Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(VendorNotFoundException::new);
    return new VendorDTO(
        vendor, userService.findById(vendor.getUserId()));
  }

  public VendorDTO profile(Authentication authentication) throws UserNotFoundException {
    User user = (User) authentication.getPrincipal();
    return new VendorDTO(findByUserId(user.getId()));
  }

  public Page<Vendor> all(Pageable pageable) {
    return vendorRepository.findAll(pageable);
  }

  public Page<VendorDTO> allDTOs(Pageable pageable) {
    return all(pageable).map(VendorDTO::new);
  }

  public Vendor findByUserId(Long userId) throws UserNotFoundException {
    return vendorRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
  }
}

