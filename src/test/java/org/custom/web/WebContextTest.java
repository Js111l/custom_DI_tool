package org.custom.web;

import org.custom.core.appcontext.AppContext;
import org.junit.jupiter.api.Test;

public class WebContextTest {

  private static final String PRICE_CONTROLLER_TEST =
      "org.custom.appcontext.testobj.web.testcontroller1";
  private static final String USER_CONTROLLER_TEST =
      "org.custom.appcontext.testobj.web.testcontroller2";
  private static final String PRODUCT_CONTROLLER_TEST =
      "org.custom.appcontext.testobj.web.testcontroller3";
  private static final String ALL_CONTROLLERS_TEST =
      "org.custom.appcontext.testobj.web.allcontrollers";

  /*
  In order to test web functionality, import specific collections to postman (from java/resources) and test each controller or all
  controllers at once.

   */
  @Test
  public void priceControllerTest() {
    var context = new AppContext(PRICE_CONTROLLER_TEST);
    while (true) {

    }
  }

  @Test
  public void userControllerTest() {
    var context = new AppContext(USER_CONTROLLER_TEST);
    while (true) {

    }
  }

  @Test
  public void productControllerTest() {
    var context = new AppContext(PRODUCT_CONTROLLER_TEST);
    while (true) {

    }
  }

  @Test
  public void allControllersTest() {
    var context = new AppContext(ALL_CONTROLLERS_TEST);
    while (true) {

    }
  }
}
