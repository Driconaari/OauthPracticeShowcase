package com.authproject.auth;

import com.authproject.util.JwtUtils;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;

public class AuthHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        try {
            // In a real app, you'd parse POST body for credentials
            // For this refresh, let's assume login is successful:
            String token = JwtUtils.createToken("developer_user");

            String response = "{\"access_token\":\"" + token + "\"}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length());

            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}