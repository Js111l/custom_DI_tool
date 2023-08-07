package org.custom.appcontext.testobj.defaultannotationtest.withdefaultannotation.sameinterfacetestobjects;

import org.custom.annotations.Item;

@Item
public class SmsService2 implements MessageService2 {

  @Override
  public void sendMessage(String message) {
    System.out.println("Sending SMS: " + message);
  }
}
