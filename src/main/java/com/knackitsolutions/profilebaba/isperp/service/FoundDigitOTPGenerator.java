package com.knackitsolutions.profilebaba.isperp.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class FoundDigitOTPGenerator implements OTPGenerator {

  public String generateOTP() {
    Random random = new Random();
    int i = random.nextInt(9999);
    return String.valueOf(i);
  }
}
