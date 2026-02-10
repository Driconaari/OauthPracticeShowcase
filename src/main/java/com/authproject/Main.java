package com.authproject;

import com.authproject.auth.AuthHandler;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/login", new AuthHandler());

        server.setExecutor(null);
        System.out.println("Server started. Listening on port 8080");
        server.start();
    }
}