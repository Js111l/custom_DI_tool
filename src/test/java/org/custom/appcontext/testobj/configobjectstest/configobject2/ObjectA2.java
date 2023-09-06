package org.custom.appcontext.testobj.configobjectstest.configobject2;

import org.custom.core.annotations.Item;

@Item
public class ObjectA2 {

  private final ObjectC2 cls;

  public ObjectA2(ObjectC2 cls) {
    this.cls = cls;
  }

  public ObjectC2 getCls() {
    return cls;
  }
}
