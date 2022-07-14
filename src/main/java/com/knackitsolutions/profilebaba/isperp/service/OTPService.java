package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.exception.InvalidOTPException;
import com.knackitsolutions.profilebaba.isperp.exception.OTPNotSentException;

public interface OTPService {

  String sendOTP(String phoneNumber) throws OTPNotSentException;

  void validateOTP(String phoneNumber, String otp) throws InvalidOTPException;

  void invalidateOTP(String phoneNumber);
}

