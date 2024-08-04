package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.dto.UserDTO;
import com.knackitsolutions.profilebaba.isperp.service.OTPService;
import com.knackitsolutions.profilebaba.isperp.service.impl.AdminService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final OTPService otpService;
    @Value("${spring.profiles.active}")
    private List<String> profiles;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> newAdmin(@RequestBody AdminSignUp signUp) {
        UserDTO signup = adminService.signup(signUp);
        HttpHeaders httpHeaders = new HttpHeaders();
        if (profiles.stream().anyMatch(s -> s.equals("local"))) {
            httpHeaders.set("otp", otpService.sendOTP(signup.getPhoneNumber()));
        }
        return new ResponseEntity<>(signup, httpHeaders, HttpStatus.OK);
    }

    @RequiredArgsConstructor
    @Data
    public static class AdminSignUp{
        private final String phoneNumber;
        private final String password;
    }
}
