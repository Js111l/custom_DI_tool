package org.custom.appcontext.testobj.circulardependentobjects.simpletest;

import org.custom.annotations.Item;

@Item
public class Circular2 {
private final Circular1 circular1;

  public Circular2(Circular1 circular1) {
    this.circular1 = circular1;
  }
}
