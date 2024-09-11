package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.controller.VendorController.LoginRequest;
import com.knackitsolutions.profilebaba.isperp.controller.VendorController.SetPasswordRequest;
import com.knackitsolutions.profilebaba.isperp.dto.*;
import com.knackitsolutions.profilebaba.isperp.entity.main.AdminUser;
import com.knackitsolutions.profilebaba.isperp.entity.main.User;
import com.knackitsolutions.profilebaba.isperp.entity.main.Vendor;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Customer;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Employee;
import com.knackitsolutions.profilebaba.isperp.enums.UserType;
import com.knackitsolutions.profilebaba.isperp.exception.*;
import com.knackitsolutions.profilebaba.isperp.helper.UserServiceHelper;
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
  private final UserServiceHelper userServiceHelper;


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
    Map<String, Object> expectedMap = getMapFromIoJsonWebTokenClaims(claims);
    String token = jwtTokenUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
    return ResponseEntity.ok(new JwtResponse(token));
  }

  public Map<String, Object> getMapFromIoJsonWebTokenClaims(DefaultClaims claims) {
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

  @GetMapping("/profile")
  public ResponseEntity<?> profile() throws UserNotFoundException {
    Authentication authentication = authenticationFacade.getAuthentication();
    User userDetails = (User) authentication.getPrincipal();
    UserCommonInfo userInfo = userServiceHelper.getUserInfo(userDetails);
    if (userDetails.getUserType() == UserType.ISP) {
      return ResponseEntity.ok(new VendorDTO((Vendor) userInfo));
    } else if (userDetails.getUserType() == UserType.EMPLOYEE) {
      return ResponseEntity.ok(new EmployeeDTO((Employee) userInfo));
    } else if (userDetails.getUserType() == UserType.CUSTOMER) {
      return ResponseEntity.ok(new CustomerDTO((Customer) userInfo));
    } else if (userDetails.getUserType() == UserType.ADMIN){
      return ResponseEntity.ok(new AdminUserDTO((AdminUser) userInfo));
    }
    throw new UserNotFoundException();
  }

  @PostMapping("/send-otp")
  public ResponseEntity<GenericResponse> sendOTP(@RequestBody SendOTPRequest request)
          throws OTPNotSentException {
    return ResponseEntity.ok().body(authenticationService.sendOTP(request.getPhoneNumber()));
  }

  @PostMapping("/validate-otp")
  public ResponseEntity<GenericResponse> validateOTP(@RequestBody ValidateOTPRequest request)
          throws InvalidOTPException, UserNotFoundException {
    return ResponseEntity.ok()
            .body(authenticationService.validateOTP(request.getPhoneNumber(), request.getOtp()));
  }

  @Data
  public static class ChangePassword {
    private String phoneNumber;
    private String oldPassword;
    private String newPassword;
  }

  @Data
  public static class SendOTPRequest{
    private String phoneNumber;
  }
  @Data
  public static class ValidateOTPRequest {
    private String phoneNumber;
    private String otp;
  }

}
