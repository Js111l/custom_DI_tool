package org.custom.web.testobj.allcontrollers.controllerditest;

import lombok.Data;
import org.custom.core.annotations.Item;

@Item
@Data
public class Dog {

  private String name;

  public Dog(String name) {
    this.name = name;
  }

  public Dog() {

  }

  public void bark() {
    System.out.println("Bark!");
  }
}
