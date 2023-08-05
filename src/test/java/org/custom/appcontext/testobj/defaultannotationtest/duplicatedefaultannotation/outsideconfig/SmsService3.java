package org.custom.appcontext.testobj.defaultannotationtest.duplicatedefaultannotation.outsideconfig;

import org.custom.annotations.Default;
import org.custom.annotations.Item;
import org.custom.appcontext.testobj.defaultannotationtest.duplicatedefaultannotation.outsideconfig.MessageService3;

@Item
@Default
public class SmsService3 implements MessageService3 {

  @Override
  public void sendMessage(String message) {
    System.out.println("Sending SMS: " + message);
  }
}
