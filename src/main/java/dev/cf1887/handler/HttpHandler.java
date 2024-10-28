package dev.cf1887.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Optional;

import dev.cf1887.decoder.HttpDecoder;
import dev.cf1887.request.HttpRequest;
import dev.cf1887.response.HttpResponse;

public class HttpHandler implements Runnable {

    private final Socket client;

    /**
     * Constructor
     * 
     * @param client instance of client connection
     */
    public HttpHandler(Socket client) {
        this.client = client;
    }

    /**
     * Main method that is executed whenever this runnable class is called
     */
    @Override
    public void run() {
        try {
            handleConnection(client);
        } catch (IOException e) {
            e.printStackTrace();
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
}
