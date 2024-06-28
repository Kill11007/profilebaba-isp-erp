package com.knackitsolutions.profilebaba.isperp.entity.tenant;

import com.knackitsolutions.profilebaba.isperp.dto.CustomerCareContactDTO;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer_care_contacts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
//This will be visible to customers
public class CustomerCareContact {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String contactNumber;

  public CustomerCareContact(CustomerCareContactDTO dto) {
    this.name = dto.getName();
    this.contactNumber = dto.getContactNumber();
  }
}
