package org.custom.appcontext.testobj;

import org.custom.annotations.Item;

@Item
public class A {
private final B bObj;

  public A(B bObj) {
    this.bObj = bObj;
  }

  public B getbObj() {
    return bObj;
  }
}