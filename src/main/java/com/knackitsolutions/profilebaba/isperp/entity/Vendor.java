package com.knackitsolutions.profilebaba.isperp.entity;

import java.util.Collection;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "isps")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Vendor implements UserDetails {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long id;
  private String phoneNumber;
  private String businessName;
  private String password;
  @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
  private Boolean rememberMe;
  @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
  private Boolean isPhoneNumberVerified;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Vendor isp = (Vendor) o;
        return id != null && Objects.equals(id, isp.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
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
