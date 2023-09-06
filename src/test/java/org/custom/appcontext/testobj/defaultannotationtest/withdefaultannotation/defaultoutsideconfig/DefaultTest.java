package org.custom.appcontext.testobj.defaultannotationtest.withdefaultannotation.defaultoutsideconfig;

import org.custom.core.annotations.Default;
import org.custom.core.annotations.Item;

@Default
@Item
public class DefaultTest {

  public void hello() {
    System.out.println("default object");
  }
}
