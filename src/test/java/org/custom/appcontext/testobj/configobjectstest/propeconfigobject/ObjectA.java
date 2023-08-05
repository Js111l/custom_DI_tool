package org.custom.appcontext.testobj.configobjectstest.propeconfigobject;

import org.custom.annotations.Item;

@Item
public class ObjectA {

  private final ObjectC cls;

  public ObjectA(ObjectC cls) {
    this.cls = cls;
  }

  public ObjectC getCls() {
    return cls;
  }
}
