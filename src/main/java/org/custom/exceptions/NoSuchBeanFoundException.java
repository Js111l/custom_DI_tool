package org.custom.exceptions;

public class NoSuchBeanFoundException extends RuntimeException {

  public NoSuchBeanFoundException(String message) {
    super(message);
  }
}
