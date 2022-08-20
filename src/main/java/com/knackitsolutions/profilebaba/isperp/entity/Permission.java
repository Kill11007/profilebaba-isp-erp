package com.knackitsolutions.profilebaba.isperp.entity;

import com.knackitsolutions.profilebaba.isperp.dto.PermissionDTO;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToMany(mappedBy = "permissions")
  private Set<Employee> employees;

  public Permission(PermissionDTO dto) {
    this.name = dto.getName();
  }

  public void update(PermissionDTO dto) {
    this.setName(dto.getName());
  }
}
