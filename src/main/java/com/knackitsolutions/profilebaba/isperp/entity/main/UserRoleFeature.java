package com.knackitsolutions.profilebaba.isperp.entity.main;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.knackitsolutions.profilebaba.isperp.enums.UserType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_role_features")
@Getter
@Setter
@NoArgsConstructor
public class UserRoleFeature {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private UserType userType;
  @JsonBackReference
  @JoinColumn(name = "permission_id")
  @ManyToOne(fetch = FetchType.EAGER)
  private Permission permission;
}
