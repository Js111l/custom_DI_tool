package org.custom.appcontext.testobj.dependencyinjection.classtoinjectnotinthecontext;

import org.custom.core.annotations.Item;

@Item
public class A2 {

  private final B2 bObj;

  public A2(B2 bObj) {
    this.bObj = bObj;
  }

  public B2 getB() {
    return bObj;
  }
}