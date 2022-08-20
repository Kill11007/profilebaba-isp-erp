package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.exception.InvalidOTPException;
import com.knackitsolutions.profilebaba.isperp.exception.OTPNotSentException;
import com.knackitsolutions.profilebaba.isperp.service.OTPGenerator;
import com.knackitsolutions.profilebaba.isperp.service.OTPService;
import com.knackitsolutions.profilebaba.isperp.utility.RedisUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Log4j2
@Profile("prd")
@RequiredArgsConstructor
public class SMSOTPService implements OTPService {

  private final OTPGenerator otpGenerator;
  private final RedisUtility redisUtility;
  @Override
  public String sendOTP(String phoneNumber) throws OTPNotSentException {
    if (StringUtils.hasLength(redisUtility.getValue(phoneNumber))) {
      throw new OTPNotSentException();
    }
    String otp = otpGenerator.generateOTP();
    log.info("OTS sent on phone number: " + phoneNumber);
    redisUtility.setValue(phoneNumber, otp);
    return "";
  }

  @Override
  public void validateOTP(String phoneNumber, String otp) throws InvalidOTPException {
    String cachedOTP = redisUtility.getValue(phoneNumber);
    boolean isEquals = cachedOTP.equals(otp);
    if (isEquals) {
      invalidateOTP(phoneNumber);
    }else {
      throw new InvalidOTPException();
    }
  }

  @Override
  public void invalidateOTP(String phoneNumber) {
    redisUtility.deleteValue(phoneNumber);
  }
}
