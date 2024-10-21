package dev.cf1887.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

import dev.cf1887.decoder.HttpDecoder;
import dev.cf1887.request.HttpRequest;

public class Server {
    
    private int port;
    private ServerSocket serverSocket;

    /**
     * Constructor
     * @param port port that the server will listen to
     * @throws IOException
     */
    public Server(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
    }

    /**
     * Main method to start the server
     * @throws IOException
     */
    public void start() throws IOException {
        while (true) {
            // Listen for incoming connections (and enter blocking state)
            Socket client = this.serverSocket.accept();
            handleConnection(client);
        }
    }

    /**
     * This method handles the incoming connection
     * @param client incoming client connection
     * @throws IOException
     */
    private void handleConnection(Socket client) throws IOException {
        Optional<HttpRequest> result = HttpDecoder.decode(client.getInputStream());
        // If decode was successfull
        if (result.isPresent()) {
            HttpRequest request = result.get();
            // TODO: Process the request
            System.out.println("Method: " + request.getMethod());
            System.out.println("Path: " + request.getPath());
            System.out.println("Version: " + request.getVersion());
            System.out.println("Headers: " + String.join(System.lineSeparator(), request.getHeaders().entrySet().stream().map((entry) -> entry.getKey() + ": " + entry.getValue()).toList()));
            System.out.println("Body: " + request.getBody());
        }
    }

    /* ******* *
     * Getters *
     ********* */

    public int getPort() {
        return port;
    }
}
