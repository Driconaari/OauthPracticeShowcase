package com.authproject;

import com.authproject.auth.AuthHandler;
import com.sun.net.httpserver.HttpServer;
import com.authproject.resource.DataHandler;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

//the main class to start the server and set up the endpoints
//like a controller in a more complex framework, or router
//this is in main because it's the entry point of the application, and we want to keep it simple without adding more layers of abstraction for this demo
public class Main {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // 1. The Api Login
        server.createContext("/login", new AuthHandler());

        //2
        server.createContext("/api/data", new DataHandler());

        //3. The Static File endpoint
        server.createContext("/", exchange -> {
            //we check if the request is for the root path, if not we can return a 404 or serve other static files
                if (!exchange.getRequestURI().getPath().equals("/")) {
                exchange.sendResponseHeaders(404, -1);
                exchange.close();
                return;
            }

            InputStream is = Main.class.getResourceAsStream("/login.html");
            if (is == null) {
                String msg = "File not found! Ensure login.html is in the resources folder.";
                exchange.sendResponseHeaders(404, msg.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(msg.getBytes());
                }
            } else {
                byte[] responseData = is.readAllBytes();
                exchange.getResponseHeaders().set("Content-Type", "text/html");
                exchange.sendResponseHeaders(200, responseData.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(responseData);
                }
            }
                exchange.close();
        });

        server.setExecutor(null);
        System.out.println("Server started. Listening on port 8080");
        server.start();
    }
}