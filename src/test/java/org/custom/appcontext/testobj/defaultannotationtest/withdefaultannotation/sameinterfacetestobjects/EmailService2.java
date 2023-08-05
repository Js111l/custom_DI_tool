package org.custom.appcontext.testobj.defaultannotationtest.withdefaultannotation.sameinterfacetestobjects;

import org.custom.annotations.Default;
import org.custom.annotations.Item;

@Item
@Default
public class EmailService2 implements MessageService2 {

  @Override
  public void sendMessage(String message) {
    System.out.println("Sending Email: " + message);
  }
}
