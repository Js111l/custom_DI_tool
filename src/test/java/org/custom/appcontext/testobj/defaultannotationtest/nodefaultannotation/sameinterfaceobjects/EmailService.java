package org.custom.appcontext.testobj.defaultannotationtest.nodefaultannotation.sameinterfaceobjects;

import org.custom.annotations.Item;

@Item
public class EmailService implements MessageService {

  @Override
  public void sendMessage(String message) {
    System.out.println("Sending Email: " + message);
  }
}
