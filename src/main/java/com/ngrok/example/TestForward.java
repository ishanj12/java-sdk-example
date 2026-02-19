package com.ngrok.example;

import com.ngrok.Session;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;

public class TestForward {
    public static void main(String[] args) throws Exception {
        // Simple server on port 8080 (simulates "existing app")
        var server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", exchange -> {
            var response = "Hello from existing Java app!\n";
            exchange.sendResponseHeaders(200, response.length());
            try (var os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        });
        server.start();
        System.out.println("Existing app on http://localhost:8080");

        // --- Snippet from README "Add to Existing Code" ---
        var session = Session.withAuthtokenFromEnv().connect();
        var forwarder = session.httpEndpoint().forward(new URL("http://localhost:8080"));
        System.out.println("Ingress established at: " + forwarder.getUrl());

        Thread.currentThread().join();
    }
}
