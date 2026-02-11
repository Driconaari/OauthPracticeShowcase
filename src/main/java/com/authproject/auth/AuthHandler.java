package com.authproject.auth;

import com.authproject.model.User;
import com.authproject.util.JSONParser;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;


public class AuthHandler implements HttpHandler {
    private TokenProvider tokenProvider = new TokenProvider();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }
        try {
            //read the actual body
            //1 - In a real app, you'd validate the username and password here
            InputStream is = exchange.getRequestBody();
            String body = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
                    .lines().collect(Collectors.joining("\n"));
            //2
            String submittedUser = body.contains("\"username") ? body.split("\"username\":\"")[1].split("\"")[0] : "";
            String submittedPass = body.contains("\"password") ? body.split("\"password\":\"")[1].split("\"")[0] : "";
            //3
            User mockDatabaseUser = new User ("admin", "password123");

            String response;
            int statusCode;

            //4 - Validate the credentials
            if (mockDatabaseUser.getUsername().equals(submittedUser) &&
                mockDatabaseUser.getPassword().equals(submittedPass)) {

                String token = tokenProvider.generateTokenForUser(mockDatabaseUser);
                response = JSONParser.createTokenResponse(token);
                statusCode = 200;
            } else {
                response = "{\"error\":\"Invalid credentials\"}";
                statusCode = 401;
            }

            //5 - Send the response
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(statusCode, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }

        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(500, -1);
        }
    }
}