package com.reliaquest.api.exception;

public class DuplicateEmployeeException extends BaseEmployeeException {

  private String message;

  public DuplicateEmployeeException() {
    super();
  }

  public DuplicateEmployeeException(String message) {
    super(message);
    this.message = message;
  }

  public DuplicateEmployeeException(String message, Throwable cause) {
    super(message, cause);
    this.message = message;
  }
}
