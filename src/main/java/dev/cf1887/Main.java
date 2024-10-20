package dev.cf1887;

import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starte Server...");
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("... Server gestartet!");
            while (true) {
                try (Socket client = serverSocket.accept()) {
                    // TODO: Parse Requests
                    System.out.println(client.toString());
                }
                catch (Exception e) {
                    // TODO: handle exception
                    System.err.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e.getMessage());
        }
        
    }
}