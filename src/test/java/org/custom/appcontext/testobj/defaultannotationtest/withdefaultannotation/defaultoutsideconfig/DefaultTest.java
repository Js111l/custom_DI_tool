package org.custom.appcontext.testobj.defaultannotationtest.withdefaultannotation.defaultoutsideconfig;

import org.custom.annotations.Default;
import org.custom.annotations.Item;

@Default
@Item
public class DefaultTest {

  public void hello() {
    System.out.println("default object");
  }
}
