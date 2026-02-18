package com.ngrok.example;

import com.ngrok.Session;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws IOException {
        // Start a simple HTTP server
        var server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", exchange -> {
            System.out.printf("%s %s%n", exchange.getRequestMethod(), exchange.getRequestURI());
            var response = "Hello from ngrok-java!\n";
            exchange.sendResponseHeaders(200, response.length());
            try (var os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        });
        server.start();

        // Forward ngrok traffic to the local server
        try (var session = Session.withAuthtokenFromEnv().connect()) {
            var forwarder = session.httpEndpoint().forward(new URL("http://localhost:8080"));
            System.out.println("Ingress established at: " + forwarder.getUrl());
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
