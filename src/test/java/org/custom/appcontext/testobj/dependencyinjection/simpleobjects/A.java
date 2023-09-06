package org.custom.appcontext.testobj.dependencyinjection.simpleobjects;

import org.custom.core.annotations.Item;

@Item
public class A {

  private final B bObj;

  public A(B bObj) {
    this.bObj = bObj;
  }

  public B getB() {
    return bObj;
  }
}