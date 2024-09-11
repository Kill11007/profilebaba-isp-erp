package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.main.AdminUser;
import lombok.Data;

@Data
public class AdminUserDTO {
    private Long id;
    private String name;
    private Long userId;
    private String phoneNumber;
    private String address;

    public AdminUserDTO(Long id, String name, Long userId, String phoneNumber, String address) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public AdminUserDTO(AdminUser adminUser) {
        this(adminUser.getId(), adminUser.getName(), adminUser.getUserId(), adminUser.getPhoneNumber(), adminUser.getAddress());
    }
}
