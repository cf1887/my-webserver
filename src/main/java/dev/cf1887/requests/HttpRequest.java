package dev.cf1887.requests;

import java.net.Socket;

public class HttpRequest {
    
    private String clientString;

    private HttpRequest(String clientString) {
        // TODO: Parsed request parameters (method, headers, body, etc)
        this.clientString = clientString;
    }

    public static HttpRequest parse(Socket client) {
        return new HttpRequest(client.toString());
    }

    public String getClientString() {
        return this.clientString;
    }

}
