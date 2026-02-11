package com.authproject.auth;

import com.authproject.model.User;
import com.authproject.util.JSONParser;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;


public class AuthHandler implements HttpHandler {

    private TokenProvider tokenProvider = new TokenProvider();

    @Override
    public void handle(HttpExchange exchange) {
        try {
            //1
            User user = new User("dev_user", "password123");
            //2
            String token = tokenProvider.generateTokenForUser(user);
            //3
            String response = JSONParser.createTokenResponse(token);


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