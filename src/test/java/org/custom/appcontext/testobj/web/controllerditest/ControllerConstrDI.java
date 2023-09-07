package org.custom.appcontext.testobj.web.controllerditest;

import org.custom.appcontext.testobj.web.allcontrollers.Dog;
import org.custom.web.annotations.Get;
import org.custom.web.annotations.PathVariable;
import org.custom.web.annotations.RestController;
import org.custom.web.model.HttpStatusCode;
import org.custom.web.model.RestResponse;
@RestController
public class ControllerConstrDI {

  private final Dog dog;

  public ControllerConstrDI(Dog dog) {
    this.dog = dog;
  }

  @Get(url = "/dog/{id}")
  public RestResponse<String> getBark(@PathVariable long id) {
    dog.bark();
    return new RestResponse<>("cat meows!", HttpStatusCode.OK);
  }
}
