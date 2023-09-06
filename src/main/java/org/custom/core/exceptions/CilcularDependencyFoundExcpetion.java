package org.custom.core.exceptions;

import java.io.Serial;

public class CilcularDependencyFoundExcpetion extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 3L;

  public CilcularDependencyFoundExcpetion(String message) {
    super(message);
  }
}
