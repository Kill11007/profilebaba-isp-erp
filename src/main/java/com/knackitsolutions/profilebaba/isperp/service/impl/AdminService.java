package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.controller.AdminController;
import com.knackitsolutions.profilebaba.isperp.dto.UserDTO;
import com.knackitsolutions.profilebaba.isperp.entity.main.User;
import com.knackitsolutions.profilebaba.isperp.enums.UserType;
import com.knackitsolutions.profilebaba.isperp.exception.UserAlreadyExistsException;
import com.knackitsolutions.profilebaba.isperp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    public UserDTO signup(AdminController.AdminSignUp signUp) {
        Boolean exists = userService.existsByPhoneNumber(signUp.getPhoneNumber());
        if (exists) {
            throw new UserAlreadyExistsException("User already exists with phone number: " + signUp.getPhoneNumber());
        }
        User user = new User();
        user.setPassword(passwordEncoder.encode(signUp.getPassword()));
        user.setUserType(UserType.ADMIN);
        user.setPhoneNumber(signUp.getPhoneNumber());
        user.setTenantId("BOOTSTRAP");
        User saved = userService.save(user);
        return new UserDTO(saved);
    }
}
