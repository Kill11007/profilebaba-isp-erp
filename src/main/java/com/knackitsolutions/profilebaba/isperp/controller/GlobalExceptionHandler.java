package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.dto.ErrorDTO;
import com.knackitsolutions.profilebaba.isperp.dto.ErrorResponse;
import com.knackitsolutions.profilebaba.isperp.exception.BusinessNameNotUniqueException;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerAlreadyExistsException;
import com.knackitsolutions.profilebaba.isperp.exception.CustomerNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.HardwareNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.InvalidLoginCredentialException;
import com.knackitsolutions.profilebaba.isperp.exception.NonRefreshableTokenException;
import com.knackitsolutions.profilebaba.isperp.exception.PlanNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.ServiceAreaAlreadyExistsException;
import com.knackitsolutions.profilebaba.isperp.exception.ServiceAreaNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.VendorNotFoundException;
import com.knackitsolutions.profilebaba.isperp.exception.OTPNotSentException;
import com.knackitsolutions.profilebaba.isperp.exception.PhoneNumberAlreadyExistException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = InvalidLoginCredentialException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ErrorResponse handleForbidden(InvalidLoginCredentialException exception) {
    return handleException(exception);
  }

  @ExceptionHandler(value = BusinessNameNotUniqueException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleUniqueBusinessName(
      BusinessNameNotUniqueException businessNameNotUniqueException) {
    return handleException(businessNameNotUniqueException);
  }

  @ExceptionHandler(value = VendorNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleVendorNotFound(
      VendorNotFoundException vendorNotFoundException) {
    return handleException(vendorNotFoundException);
  }

  @ExceptionHandler(value = OTPNotSentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleOTPNotSent(
      OTPNotSentException otpNotSentException) {
    return handleException(otpNotSentException);
  }

  @ExceptionHandler(value = PhoneNumberAlreadyExistException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handlePhoneNumberExists(
      PhoneNumberAlreadyExistException phoneNumberAlreadyExistException) {
    return handleException(phoneNumberAlreadyExistException);
  }

  @ExceptionHandler(value = NonRefreshableTokenException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleRefreshTokenException(NonRefreshableTokenException exception) {
    return handleException(exception);
  }

  @ExceptionHandler(value = PlanNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handlePlanNotFound(PlanNotFoundException exception) {
    return handleException(exception);
  }

  @ExceptionHandler(value = {CustomerNotFoundException.class, CustomerAlreadyExistsException.class,
      HardwareNotFoundException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleCustomerException(Exception exception) {
    return handleException(exception);
  }

  @ExceptionHandler(value = {ServiceAreaNotFoundException.class, ServiceAreaAlreadyExistsException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleServiceAreaException(Exception exception) {
    return handleException(exception);
  }

  private ErrorResponse handleException(Throwable exception) {
    Throwable cause = exception.getCause();
    return new ErrorResponse(List.of(
        new ErrorDTO(findCode(), cause == null ? "" : cause.getMessage(),
            exception.getMessage())));
  }

  private String findCode() {
    //TODO
    return "";
  }
}
