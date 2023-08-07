package org.custom.exceptions;

public class CilcularDependencyFoundExcpetion extends RuntimeException {

  private static final long serialVersionUID = 3l;

  public CilcularDependencyFoundExcpetion(String message) {
    super(message);
  }
}
