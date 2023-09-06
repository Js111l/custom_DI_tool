package org.custom.web;

import org.custom.core.appcontext.AppContext;
import org.junit.jupiter.api.Test;

public class WebContextTest {
  private static final String USER_CONTROLLER_TEST =
      "package org.custom.appcontext.testobj.web.testcontroller1;";
  private static final String PRICE_CONTROLLER_TEST =
      "package org.custom.appcontext.testobj.web.testcontroller2;";
  private static final String PRODUCT_CONTROLLER_TEST =
      "package org.custom.appcontext.testobj.web.testcontroller3;";

  @Test
  public void loadsController() {
    new AppContext(USER_CONTROLLER_TEST);
  }
}
