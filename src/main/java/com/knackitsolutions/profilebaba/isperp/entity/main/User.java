package com.knackitsolutions.profilebaba.isperp.entity.main;

import com.knackitsolutions.profilebaba.isperp.enums.UserType;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
public class User implements UserDetails {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long id;
  private String tenantId;

  private String phoneNumber;
  private String password;
  @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
  private Boolean rememberMe = false;
  @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
  private Boolean isPhoneNumberVerified = false;

  @Column(columnDefinition = "ENUM('ADMIN', 'EMPLOYEE', 'ISP', 'CUSTOMER')")
  private UserType userType;

  /*@ManyToMany
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id")
      , inverseJoinColumns = @JoinColumn(name = "role_id"))
  @Exclude
  private Set<Role> roles;*/

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_permissions", joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id"))
  private Set<Permission> permissions;

  @Transient
  private List<GrantedAuthority> userGrantedAuthorities;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (userGrantedAuthorities == null || userGrantedAuthorities.isEmpty()) {
      userGrantedAuthorities = permissions.stream()
          .map(permission -> new SimpleGrantedAuthority("ROLE_" + permission.getName())).collect(
              Collectors.toList());
    }
    return userGrantedAuthorities;
  }

  @Override
  public String getUsername() {
    return this.phoneNumber;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return isPhoneNumberVerified;
  }
}
