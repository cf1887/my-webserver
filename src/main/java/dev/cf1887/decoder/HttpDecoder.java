package dev.cf1887.decoder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import dev.cf1887.exception.InvalidRequestLineException;
import dev.cf1887.request.HttpRequest;
import dev.cf1887.util.HttpMethod;

public class HttpDecoder {

    /**
     * Main method for decoding an incoming request into a HttpRequest class
     * 
     * @param is the clients inputStream
     * @return
     */
    public static Optional<HttpRequest> decode(final InputStream is) {
        try {
            // Access to the client inputStream
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            // 1. Parse request line (required)
            String requestLine = br.readLine();
            if (requestLine == null || requestLine.isEmpty()) {
                return Optional.empty();
            }
            // Split request line into its three parts and get the information out of them
            String[] requestLineParts = requestLine.split(" ");
            if (requestLineParts.length != 3) {
                throw new InvalidRequestLineException();
            }
            HttpMethod method = HttpMethod.valueOf(requestLineParts[0].toUpperCase());
            String path = requestLineParts[1];
            String version = requestLineParts[2];
            // 2. Parse header section (optional, but usually given)
            Map<String, String> headers = new HashMap<>();
            // Iterate over the clients inputStream line by line until first appearance of
            // CRLF or null
            while (br.ready()) {
                String line = br.readLine();
                if (line == null || line.isEmpty()) {
                    break;
                }
                // Split header in key-value-pairs by first occurance of ":"
                int colonIndex = line.indexOf(":");
                // If there is no colon found
                if (colonIndex == -1) {
                    // TODO: Proper error handling
                    // For now: Print a warning and ignore the header
                    System.out.println("Warning: Ignoring malformed header: " + line);
                    continue;
                }
                String key = line.substring(0, colonIndex).trim();
                String value = line.substring(colonIndex + 1).trim();
                headers.put(key, value);
            }
            // 3. Parse body section (optional)
            StringBuilder body = new StringBuilder();
            // Read the rest as character stream to string
            while (br.ready()) {
                body.append((char) br.read());
            }
            // Create Optional of new HttpRequest instance
            return Optional.of(new HttpRequest(method, path, version, headers, body.toString()));
        } catch (Exception exception) {
            // TODO: Proper exception handling
            return Optional.empty();
        }
    }
}
