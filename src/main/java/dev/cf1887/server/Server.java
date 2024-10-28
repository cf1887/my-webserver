package dev.cf1887.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

import dev.cf1887.decoder.HttpDecoder;
import dev.cf1887.request.HttpRequest;
import dev.cf1887.response.HttpResponse;

public class Server {

    private int port;
    private ServerSocket serverSocket;

    /**
     * Constructor
     * 
     * @param port port that the server will listen to
     * @throws IOException
     */
    public Server(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
    }

    /**
     * Main method to start the server
     * 
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
     * 
     * @param client incoming client connection
     * @throws IOException
     */
    private void handleConnection(Socket client) throws IOException {
        Optional<HttpRequest> result = HttpDecoder.decode(client.getInputStream());
        // If decode was successfull
        if (result.isPresent()) {
            // Get the request instance
            HttpRequest request = result.get();
            // Build the response
            HttpResponse response = new HttpResponse.Builder()
                    .withContent("Echo: " + request.getBody())
                    .build();
            // Send the response
            OutputStream os = client.getOutputStream();
            os.write(response.toString().getBytes());
            os.flush();
            os.close();
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
