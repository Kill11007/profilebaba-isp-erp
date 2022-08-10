package com.knackitsolutions.profilebaba.isperp.service;

import com.knackitsolutions.profilebaba.isperp.entity.Vendor;
import com.knackitsolutions.profilebaba.isperp.repository.VendorRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class JwtUserDetailsService implements UserDetailsService {

  private final VendorRepository vendorRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //TODO improve performance using cache
    Optional<Vendor> optionalVendor = vendorRepository.findByPhoneNumber(username);
    return optionalVendor.orElseThrow(
        () -> new UsernameNotFoundException("User not found with phone number: " + username));
  }
}
