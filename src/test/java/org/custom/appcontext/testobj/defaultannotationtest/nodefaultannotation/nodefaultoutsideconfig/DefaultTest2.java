package org.custom.appcontext.testobj.defaultannotationtest.nodefaultannotation.nodefaultoutsideconfig;

import org.custom.annotations.Default;
import org.custom.annotations.Item;

//@Default  - should throw duplicate beans exception
@Item
public class DefaultTest2 {


  public DefaultTest2() {
  }


  public void hello() {
    System.out.println("duplicate bean");
  }
}