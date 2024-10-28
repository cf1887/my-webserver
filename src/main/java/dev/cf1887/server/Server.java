package dev.cf1887.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dev.cf1887.handler.HttpHandler;

public class Server {

    private final int port;
    private final ServerSocket serverSocket;
    private final ExecutorService executorService;

    /**
     * Constructor
     * 
     * @param port port that the server will listen to
     * @throws IOException
     */
    public Server(int port, int maxThreads) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
        this.executorService = Executors.newFixedThreadPool(maxThreads);
    }

    /**
     * Main method to start the server
     * 
     * @throws IOException
     */
    public void start() throws IOException {
        System.out.println("Server is listening on port " + port + "...");
        while (true) {
            // TODO: Make set of connections due to the ability of a socket to infinitely
            // reestablish new connections.
            // Solution idea: Check for existing connection and do nothing or close the
            // socket.

            // Listen for incoming connections (and enter blocking state)
            Socket client = this.serverSocket.accept();
            executorService.submit(new HttpHandler(client));
        }
    }

    /**
     * Shutdown the executor service
     */
    public void shutdown() {
        executorService.shutdown();
        try {
            serverSocket.close();
        } catch (IOException e) {
            // TODO: Proper exception handling
            e.printStackTrace();
        }
    }

    /**
     * Getter for the port that the server is listening to
     * 
     * @return current port
     */
    public int getPort() {
        return port;
    }
}
