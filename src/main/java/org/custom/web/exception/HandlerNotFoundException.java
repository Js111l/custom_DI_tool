package org.custom.web.exception;

public class HandlerNotFoundException extends RuntimeException {
  public HandlerNotFoundException(String msg) {
    super(msg);
  }
}
