package org.custom.web.server;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import org.custom.web.util.result.Result;

public final class Server {
  public static Result<HttpServer, Throwable> getServer(int port) {
    return Result.of(
        () -> {
          try {
            return HttpServer.create(new InetSocketAddress(port), 0);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
  }
}
