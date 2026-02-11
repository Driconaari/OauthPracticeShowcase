package com.authproject;

import com.authproject.auth.AuthHandler;
import com.sun.net.httpserver.HttpServer;
import com.authproject.resource.DataHandler;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

//the main class to start the server and set up the endpoints
//like a controller in a more complex framework
public class Main {
    public static void main(String[] args) throws Exception {
        // 1. The Api Login
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        //2. The Api Data - Protected Resource
        server.createContext("/login", new AuthHandler());

        //3. The Static File endpoint
        server.createContext("/", exchange -> {
            InputStream is = Main.class.getResourceAsStream("/login.html");
            if (is == null) {
                String msg = "File not found! Ensure login.html is in the resources folder.";
                exchange.sendResponseHeaders(404, msg.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(msg.getBytes());
                }
            } else {
                byte[] repsonse = is.readAllBytes();
                exchange.getResponseHeaders().set("Content-Type", "text/html");
                exchange.sendResponseHeaders(200, repsonse.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(repsonse);
                }
            }
                exchange.close();
        });

        server.setExecutor(null);
        System.out.println("Server started. Listening on port 8080");
        server.start();
    }
}