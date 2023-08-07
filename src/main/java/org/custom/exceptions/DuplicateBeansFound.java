package org.custom.exceptions;

import java.io.Serial;

public class DuplicateBeansFound extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  public DuplicateBeansFound(String message) {
    super(message);
  }
}
