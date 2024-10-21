package dev.cf1887;

import java.io.IOException;

import dev.cf1887.server.Server;

public class Main {
    public static void main(String[] args) throws IOException {
        Server server = new Server(8080);
        server.start();
    }
}