package com.authproject.resource;

import com.authproject.util.JwtUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

// resource/DataHandler.java
public class DataHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // 1. Get the Header
        String authHeader = exchange.getRequestHeaders().getFirst("Authorization");

        // 2. Validate
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                if (JwtUtils.verify(token)) {
                    String response = "{\"message\":\"This data is only visible with a valid JWT!\"}";
                    sendResponse(exchange, response, 200);
                    return;
                }
            } catch (Exception e) { /* Token tampered with */ }
        }

        // 3. Fail if token is bad
        sendResponse(exchange, "{\"error\":\"Unauthorized\"}", 403);
    }

    private void sendResponse(HttpExchange e, String res, int code) throws IOException {
        e.getResponseHeaders().set("Content-Type", "application/json");
        e.sendResponseHeaders(code, res.length());
        e.getResponseBody().write(res.getBytes());
        e.getResponseBody().close();
    }
}