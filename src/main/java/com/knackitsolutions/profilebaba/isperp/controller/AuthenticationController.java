package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.controller.VendorController.LoginRequest;
import com.knackitsolutions.profilebaba.isperp.dto.JwtResponse;
import com.knackitsolutions.profilebaba.isperp.exception.InvalidLoginCredentialException;
import com.knackitsolutions.profilebaba.isperp.exception.NonRefreshableTokenException;
import com.knackitsolutions.profilebaba.isperp.service.JwtUserDetailsService;
import com.knackitsolutions.profilebaba.isperp.utility.JwtTokenUtil;
import io.jsonwebtoken.impl.DefaultClaims;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping
public class AuthenticationController {

  private final AuthenticationManager authenticationManager;

  private final JwtTokenUtil jwtTokenUtil;

  private final JwtUserDetailsService jwtUserDetailsService;

  @PostMapping("/authenticate")
  public ResponseEntity<?> login(@RequestBody LoginRequest request)
      throws InvalidLoginCredentialException {
    authenticate(request.getPhoneNumber(), request.getPassword());
    final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(request.getPhoneNumber());
    final String token = jwtTokenUtil.generateToken(userDetails);
    return ResponseEntity.ok(new JwtResponse(token));
  }

  @GetMapping("/refresh-token")
  public ResponseEntity<?> refreshtoken(HttpServletRequest request) {
    // From the HttpRequest get the claims
    DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");
    if (claims == null) {
      throw new NonRefreshableTokenException("Token is not expired.");
    }
    Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
    String token = jwtTokenUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
    return ResponseEntity.ok(new JwtResponse(token));
  }

  public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
    Map<String, Object> expectedMap = new HashMap<>();
    for (Entry<String, Object> entry : claims.entrySet()) {
      expectedMap.put(entry.getKey(), entry.getValue());
    }
    return expectedMap;
  }

  private void authenticate(String username, String password) throws InvalidLoginCredentialException {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new InvalidLoginCredentialException("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new InvalidLoginCredentialException(e);
    } catch (Exception e) {
      throw new InvalidLoginCredentialException(e);
    }
  }
}
