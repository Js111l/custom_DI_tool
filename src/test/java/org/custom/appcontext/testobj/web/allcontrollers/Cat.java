package org.custom.appcontext.testobj.web.allcontrollers;

import lombok.Data;
import org.checkerframework.checker.units.qual.C;
import org.custom.core.annotations.Item;
import org.custom.web.annotations.Get;

@Item
@Data
public class Cat {

  private String name;

  public Cat(String name) {
    this.name = name;
  }

  public Cat() {

  }

  public void meow() {
    System.out.println("meow!");
  }
}
