package org.custom.core.exceptions;

import java.io.Serial;

public class NoSuchBeanFoundException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 2L;

  public NoSuchBeanFoundException(String message) {
    super(message);
  }
}
