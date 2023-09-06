package org.custom.appcontext.testobj.defaultannotationtest.duplicatedefaultannotation.outsideconfig;

import org.custom.core.annotations.Default;
import org.custom.core.annotations.Item;

@Item
@Default
public class EmailService3 implements MessageService3 {

  @Override
  public void sendMessage(String message) {
    System.out.println("Sending Email: " + message);
  }
}
