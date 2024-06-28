package com.knackitsolutions.profilebaba.isperp.dto;

import com.knackitsolutions.profilebaba.isperp.entity.tenant.CustomerCareContact;
import lombok.Data;

@Data
public class CustomerCareContactDTO {
  private Long id;
  private String name;
  private String contactNumber;
  public CustomerCareContactDTO(CustomerCareContact entity){
    this.id = entity.getId();
    this.name = entity.getName();
    this.contactNumber = entity.getContactNumber();
  }
}
