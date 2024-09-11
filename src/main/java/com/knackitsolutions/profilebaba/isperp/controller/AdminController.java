package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.dto.AdminUserDTO;
import com.knackitsolutions.profilebaba.isperp.dto.UserDTO;
import com.knackitsolutions.profilebaba.isperp.service.OTPService;
import com.knackitsolutions.profilebaba.isperp.service.impl.AdminService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    public ResponseEntity<AdminUserDTO> newAdmin(@RequestBody AdminSignUp signUp) {
        AdminUserDTO signup = adminService.signup(signUp);
        HttpHeaders httpHeaders = new HttpHeaders();
        if (profiles.stream().anyMatch(s -> s.equals("local"))) {
            httpHeaders.set("otp", otpService.sendOTP(signup.getPhoneNumber()));
        }
        return new ResponseEntity<>(signup, httpHeaders, HttpStatus.OK);
    }

    @NoArgsConstructor
    @Data
    @AllArgsConstructor
    public static class AdminSignUp{
        private String name;
        private String phoneNumber;
        private String password;
        private String address;
    }
}
