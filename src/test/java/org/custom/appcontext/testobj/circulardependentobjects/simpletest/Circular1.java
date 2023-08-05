package org.custom.appcontext.testobj.circulardependentobjects.simpletest;

import org.custom.annotations.Item;

@Item
public class Circular1 {

  private final Circular2 circular2;

  public Circular1(Circular2 circular2) {
    this.circular2 = circular2;
  }
}
