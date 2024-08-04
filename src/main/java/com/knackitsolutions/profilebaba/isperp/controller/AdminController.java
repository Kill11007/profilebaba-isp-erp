package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.entity.main.User;
import com.knackitsolutions.profilebaba.isperp.enums.UserType;
import com.knackitsolutions.profilebaba.isperp.service.UserService;
import com.knackitsolutions.profilebaba.isperp.service.impl.AdminService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final AdminService adminService;
    @PostMapping
    public ResponseEntity<?> newAdmin(@RequestBody AdminSignUp signUp) {
        adminService.signup(signUp);
        return ResponseEntity.noContent().build();
    }

    @RequiredArgsConstructor
    @Data
    public static class AdminSignUp{
        private final String phoneNumber;
        private final String password;
        private final UserType userType;
    }
}
