package com.reliaquest.api.exception;

public class BaseEmployeeException extends RuntimeException {

  BaseEmployeeException() {
    super();
  }

  BaseEmployeeException(String message) {
    super(message);
  }

  BaseEmployeeException(String message, Throwable cause) {
    super(message, cause);
  }
}
