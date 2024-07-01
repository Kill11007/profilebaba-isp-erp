package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.controller.VendorController.LoginRequest;
import com.knackitsolutions.profilebaba.isperp.controller.VendorController.SetPasswordRequest;
import com.knackitsolutions.profilebaba.isperp.dto.JwtResponse;
import com.knackitsolutions.profilebaba.isperp.dto.LoginResponse;
import com.knackitsolutions.profilebaba.isperp.dto.VendorDTO;
import com.knackitsolutions.profilebaba.isperp.entity.main.User;
import com.knackitsolutions.profilebaba.isperp.exception.InvalidLoginCredentialException;
import com.knackitsolutions.profilebaba.isperp.exception.InvalidOTPException;
import com.knackitsolutions.profilebaba.isperp.exception.NonRefreshableTokenException;
import com.knackitsolutions.profilebaba.isperp.exception.UserNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.VendorNotFoundException;
import com.knackitsolutions.profilebaba.isperp.service.IAuthenticationFacade;
import com.knackitsolutions.profilebaba.isperp.service.UserService;
import com.knackitsolutions.profilebaba.isperp.service.impl.AuthenticationService;
import com.knackitsolutions.profilebaba.isperp.service.impl.VendorService;
import com.knackitsolutions.profilebaba.isperp.utility.JwtTokenUtil;
import io.jsonwebtoken.impl.DefaultClaims;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/authenticate")
public class AuthenticationController {

  private final AuthenticationManager authenticationManager;

  private final JwtTokenUtil jwtTokenUtil;

  private final VendorService vendorService;
  private final UserService userService;
  private final IAuthenticationFacade authenticationFacade;
  private final AuthenticationService authenticationService;

  @PostMapping
  public ResponseEntity<?> login(@RequestBody LoginRequest request)
      throws InvalidLoginCredentialException, UserNotFoundException, VendorNotFoundException {
    authenticate(request.getPhoneNumber(), request.getPassword());
    LoginResponse login = authenticationService.login(request);
    return ResponseEntity.ok(login);
  }

  @PostMapping("/switch-vendor/{vendorId}")
  public ResponseEntity<?> switchTenant(@PathVariable Long vendorId)
      throws UserNotFoundException, VendorNotFoundException {
    VendorDTO dto = vendorService.findById(vendorId);
    User user = userService.findById(dto.getUserId());
    String token = jwtTokenUtil.generateToken(user, user.getTenantId());
    return ResponseEntity.ok(new JwtResponse(token));
  }

  @GetMapping("/refresh-token")
  public ResponseEntity<?> refreshToken(HttpServletRequest request) {
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
    return new HashMap<>(claims);
  }

  private void authenticate(String username, String password) throws InvalidLoginCredentialException {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new InvalidLoginCredentialException("USER_DISABLED", e);
    } catch (Exception e) {
      throw new InvalidLoginCredentialException(e);
    }
  }

  @PutMapping("/reset-password")
  public ResponseEntity<Void> resetPassword(@RequestBody SetPasswordRequest request)
      throws InvalidOTPException, UserNotFoundException {
    userService.resetPassword(request.getPhoneNumber(), request.getOtp(),
        request.getPassword());
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/change-password")
  public ResponseEntity<Void> changePassword(@RequestBody ChangePassword request)
      throws UserNotFoundException {
    Authentication authentication = authenticationFacade.getAuthentication();
    userService.changePassword(authentication, request);
    return ResponseEntity.noContent().build();
  }

  @Data
  public static class ChangePassword {
    private String phoneNumber;
    private String oldPassword;
    private String newPassword;
  }
}
