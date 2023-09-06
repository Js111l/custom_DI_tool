package org.custom.web.model;

import java.net.http.HttpHeaders;

public record RestResponse<T>(T body, int statusCode, HttpHeaders httpHeaders) {
  public RestResponse(T body, int statusCode) {
    this(body, statusCode, null);
  }
}
