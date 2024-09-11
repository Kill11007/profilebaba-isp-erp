package com.knackitsolutions.profilebaba.isperp.entity.main;

import com.knackitsolutions.profilebaba.isperp.controller.AdminController;
import com.knackitsolutions.profilebaba.isperp.dto.AdminUserDTO;
import com.knackitsolutions.profilebaba.isperp.dto.UserCommonInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "admin_users")
@Getter
@Setter
@NoArgsConstructor
public class AdminUser implements UserCommonInfo {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    private Long userId;
    @Column(name = "phone")
    private String phoneNumber;
    private String address;
    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    private Boolean approved;

    public AdminUser(String name, String phoneNumber, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public AdminUser(String name, Long userId, String phoneNumber, String address) {
        this.name = name;
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public AdminUser(Long id, String name, Long userId, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.phoneNumber = phoneNumber;
    }

    public AdminUser(Long id, String name, Long userId, String phoneNumber, String address) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public AdminUser(AdminController.AdminSignUp signUp) {
        this(signUp.getName(), signUp.getPhoneNumber(), signUp.getAddress());
    }

    public AdminUser(AdminController.AdminSignUp signUp, User user) {
        this(signUp.getName(), user.getId(), signUp.getPhoneNumber(), signUp.getAddress());
    }

    AdminUser update(AdminUserDTO dto) {
        Optional.ofNullable(dto.getName()).ifPresent(this::setName);
        Optional.ofNullable(dto.getAddress()).ifPresent(this::setAddress);
        return this;
    }

    @Override
    public String getUserName() {
        return this.getPhoneNumber();
    }
}
