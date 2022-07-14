package com.knackitsolutions.profilebaba.isperp.config;

import com.knackitsolutions.profilebaba.isperp.exception.OTPNotSentException;
import com.knackitsolutions.profilebaba.isperp.service.OTPGenerator;
import com.knackitsolutions.profilebaba.isperp.service.OTPService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

  @Bean
  @Profile("!prd")
  public OTPService otpService(OTPGenerator otpGenerator) {
    return new OTPService() {
      @Override
      public String sendOTP(String phoneNumber) throws OTPNotSentException {
        return otpGenerator.generateOTP();
      }

      @Override
      public void validateOTP(String phoneNumber, String otp) {
      }

      @Override
      public void invalidateOTP(String phoneNumber) {
        return;
      }
    };
  }

}
