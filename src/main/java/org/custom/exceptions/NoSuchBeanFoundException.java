package org.custom.exceptions;

public class NoSuchBeanFoundException extends RuntimeException {

  private static final long serialVersionUID = 2l;

  public NoSuchBeanFoundException(String message) {
    super(message);
  }
}
