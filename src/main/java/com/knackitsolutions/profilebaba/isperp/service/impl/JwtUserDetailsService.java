package com.knackitsolutions.profilebaba.isperp.service.impl;

import com.knackitsolutions.profilebaba.isperp.entity.main.User;
import com.knackitsolutions.profilebaba.isperp.entity.main.Vendor;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import com.knackitsolutions.profilebaba.isperp.repository.main.UserRepository;
import com.knackitsolutions.profilebaba.isperp.service.UserService;
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

  private final UserRepository userRepository;

  @Override
  public User loadUserByUsername(String username) throws UsernameNotFoundException {
    //TODO improve performance using cache
    User user;
    try {
      user = userRepository.findByPhoneNumber(username).get(0)
          .orElseThrow(() -> UserNotFoundException.withPhoneNumber(username));
    } catch (UserNotFoundException e) {
      throw new UsernameNotFoundException("No user found with phone: " + username, e);
    }
    return user;
  }
}
