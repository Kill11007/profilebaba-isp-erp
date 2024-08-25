package com.knackitsolutions.profilebaba.isperp.controller;

import com.knackitsolutions.profilebaba.isperp.dto.ErrorDTO;
import com.knackitsolutions.profilebaba.isperp.dto.ErrorResponse;
import com.knackitsolutions.profilebaba.isperp.entity.tenant.Payment;
import com.knackitsolutions.profilebaba.isperp.exception.*;

import java.util.List;
import javax.validation.ConstraintViolationException;

import com.knackitsolutions.profilebaba.isperp.service.PaymentService;
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

  @ExceptionHandler(value = PhoneNumberAlreadyExistsException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handlePhoneNumberExists(
      PhoneNumberAlreadyExistsException phoneNumberAlreadyExistsException) {
    return handleException(phoneNumberAlreadyExistsException);
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

  @ExceptionHandler(value = SubscriptionNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleSubscriptionNotFound(SubscriptionNotFoundException exception) {
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

  @ExceptionHandler(value = {PermissionNotFoundException.class, PermissionAlreadyExistsException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handlePermissionException(Exception exception) {
    return handleException(exception);
  }

  @ExceptionHandler(value = {EmployeeNotFoundException.class, EmployeeAlreadyExistsException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleEmployeeException(Exception exception) {
    return handleException(exception);
  }

  @ExceptionHandler(value = ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleConstraintViolation(ConstraintViolationException e) {
    return handleException(e);
  }

  @ExceptionHandler(value = CustomException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleCustomException(CustomException exception){
    return handleException(exception);
  }

  @ExceptionHandler(value = {PaymentService.PaymentNotFoundException.class, Payment.PaymentModeNotFoundException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handlePaymentException(Exception exception){
    return handleException(exception);
  }
//  @ExceptionHandler(value = Exception.class)
//  public ErrorResponse handleGenericException(Exception e) {
//    return handleException(e);
//  }

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
