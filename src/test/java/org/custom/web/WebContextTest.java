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
  private static final String CONTROLLERS_WITH_DI_TEST =
      "org.custom.appcontext.testobj.web.controllerditest";
  private static final String ALL_CONTROLLERS_TEST =
      "org.custom.appcontext.testobj.web.allcontrollers";

  /*
  In order to test web functionality, import specific collections to postman (from java/resources) and test each controller or all
  controllers at once.
   */
  @Test
  public void priceControllerTest() {
    initialize(PRICE_CONTROLLER_TEST);
  }

  @Test
  public void userControllerTest() {
    initialize(USER_CONTROLLER_TEST);
  }

  @Test
  public void productControllerTest() {
    initialize(PRODUCT_CONTROLLER_TEST);
  }

  @Test
  public void controllersWithDiTest() {
    initialize(CONTROLLERS_WITH_DI_TEST);
  }

  @Test
  public void allControllersTest() {
    initialize(ALL_CONTROLLERS_TEST);
  }

  private void initialize(String packageName) {
    new AppContext(packageName);
    while (true) {

    }
  }
}
