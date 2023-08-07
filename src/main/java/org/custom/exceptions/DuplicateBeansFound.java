package org.custom.exceptions;

public class DuplicateBeansFound extends RuntimeException {

  private static final long serialVersionUID = 1l;

  public DuplicateBeansFound(String message) {
    super(message);
  }
}
