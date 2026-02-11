package com.authproject.resource;

import com.authproject.auth.TokenProvider;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

// resource/DataHandler.java
public class DataHandler implements HttpHandler {

    private TokenProvider tokenProvider = new TokenProvider();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // 1. Get the Header
        String authHeader = exchange.getRequestHeaders().getFirst("Authorization");

        // 2. Validate
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                if (tokenProvider.validateToken(token)) {
                    String response = "{\"message\":\"This is protected data only visible to JWT authenticated users.\"}";
                    sendResponse(exchange, response, 200);
                    return;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

            // 3. Fail if token is bad
            sendResponse(exchange, "{\"error\":\"Unauthorized\"}", 401);
        }

        private void sendResponse(HttpExchange exchange, String response, int code) throws IOException {
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            // Use bytes.length for the header to avoid encoding issues
            byte[] responseBytes = response.getBytes();
            exchange.sendResponseHeaders(code, responseBytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseBytes);
            }
    }
}