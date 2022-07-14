package com.knackitsolutions.profilebaba.isperp.exception;

public class VendorNotFoundException extends Exception{

  public VendorNotFoundException() {
    super("Vendor not found in the database.");
  }

  public VendorNotFoundException(String message) {
    super(message);
  }
}
