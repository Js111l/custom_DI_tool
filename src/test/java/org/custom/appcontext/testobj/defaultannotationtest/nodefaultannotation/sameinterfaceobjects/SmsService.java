package org.custom.appcontext.testobj.defaultannotationtest.nodefaultannotation.sameinterfaceobjects;

import org.custom.core.annotations.Item;

@Item
public class SmsService implements MessageService {

  @Override
  public void sendMessage(String message) {
    System.out.println("Sending SMS: " + message);
  }
}
