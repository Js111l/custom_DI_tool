package org.custom.appcontext.testobj.web.controllerditest;

import org.custom.appcontext.testobj.web.allcontrollers.Cat;
import org.custom.core.annotations.Wired;
import org.custom.web.annotations.Get;
import org.custom.web.annotations.PathVariable;
import org.custom.web.annotations.RestController;
import org.custom.web.model.HttpStatusCode;
import org.custom.web.model.RestResponse;

@RestController
public class ControllerWithWired {

  @Wired
  Cat cat;

  @Get(url = "/cat/{id}")
  public RestResponse<String> getMeow(@PathVariable long id) {
    cat.meow();
    return new RestResponse<>("cat meows!", HttpStatusCode.OK);
  }
}
