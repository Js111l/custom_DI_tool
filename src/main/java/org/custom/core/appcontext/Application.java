package org.custom.core.appcontext;

import org.custom.web.WebContext;

public final class Application {

  private static final int port = 8080;

  public static AppContext run() {
    var context = new AppContext();
    final var webContext = new WebContext();
    webContext.configure(context.getContext(), port);
    webContext.initialize();
    return context;
  }

  public static AppContext run(String... packages) {
    var context = new AppContext(packages);
    final var webContext = new WebContext();
    webContext.configure(context.getContext(), port);
    webContext.initialize();
    return context;
  }
}
