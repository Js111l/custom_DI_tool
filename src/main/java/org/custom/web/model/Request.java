package org.custom.web.model;

import com.sun.net.httpserver.Headers;
import java.io.OutputStream;

public record Request(
    String requestType, Headers headers, OutputStream messageBody, java.net.URI requestURI) {}
