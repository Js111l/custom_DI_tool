package org.custom.exceptions;

public class DuplicateBeansFound extends RuntimeException {

  public DuplicateBeansFound(String message) {
    super(message);
  }
}
