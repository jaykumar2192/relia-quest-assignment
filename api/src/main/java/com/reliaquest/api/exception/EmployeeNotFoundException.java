package com.reliaquest.api.exception;

public class EmployeeNotFoundException extends BaseEmployeeException {

  private String message;

  public EmployeeNotFoundException() {
    super();
  }

  public EmployeeNotFoundException(String message) {
    super(message);
    this.message = message;
  }

  public EmployeeNotFoundException(String message, Throwable cause) {
    super(message, cause);
    this.message = message;
  }
}
