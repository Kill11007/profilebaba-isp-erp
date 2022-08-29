package com.knackitsolutions.profilebaba.isperp.entity.main;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "isps")
@Getter
@Setter
@ToString
public class Vendor {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long id;

  private String businessName;

}
