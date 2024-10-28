package dev.cf1887.response;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    // TODO: Make version and status code dynamic

    // Constant map for default headers
    private static final Map<String, String> HEADERS_DEFAULT;
    static {
        Map<String, String> buffer = new HashMap<>();
        buffer.put("Content-Type", "text/html; charset=UTF-8");
        HEADERS_DEFAULT = Collections.unmodifiableMap(buffer);
    }

    // Properties
    private final Map<String, String> headers;
    private final String content;

    /**
     * Private constructor for builder pattern usage
     * @param builder The Builder instance configuring this HttpResponse
     */
    private HttpResponse(Builder builder) {
        // Set builder headers if present, HEADERS_DEFAULT otherwise
        this.headers = new HashMap<>(builder.headers == null ? HEADERS_DEFAULT : builder.headers);
        this.content = builder.content;
    }

    /**
     * Converts the HttpResponse object into a formatted HTTP response string.
     * The format follows the HTTP/1.1 response structure, including a status line,
     * headers, a blank line, and an optional content body.
     *
     * @return a formatted HTTP response string ready for transmission
     */
    @Override
    public String toString() {
        StringBuilder responseBuilder = new StringBuilder();
        // Start with the HTTP status line (defaulting to 200 OK for simplicity)
        responseBuilder.append("HTTP/1.1 200 OK").append(System.lineSeparator());
        // Append each header as "Key: Value" followed by a newline
        for (Map.Entry<String, String> header : headers.entrySet()) {
            responseBuilder.append(header.getKey()).append(": ").append(header.getValue()).append(System.lineSeparator());
        }
        // Insert a blank line to separate headers from the content
        responseBuilder.append(System.lineSeparator());
        // Append the content body if it is not null or empty
        if (content != null && !content.isEmpty()) {
            responseBuilder.append(content);
        }
        // Return the complete HTTP response string
        return responseBuilder.toString();
    }


    /**
     * Getter for headers (unmodifiable view)
     * @return an unmodifiable map of headers
     */
    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(this.headers);
    }

    /**
     * Returns the content of the response.
     * @return the response content as a String
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Static builder class for using the builder pattern
     */
    public static class Builder {
        // Properties to be built
        private Map<String, String> headers = null;
        private String content = "";

        public Builder() {}

        /**
         * Sets the content
         * @param content the response content
         * @return the Builder instance for chaining
         */
        public Builder withContent(String content) {
            this.content = content;
            return this;
        }

        /**
         * Adds one additional header to the map of headers
         * @param key the header name
         * @param value the header value
         * @return the Builder instance for chaining
         */
        public Builder addHeader(String key, String value) {
            if (this.headers == null) {
                this.headers = new HashMap<>(HEADERS_DEFAULT);
            }
            this.headers.put(key, value);
            return this;
        }

        /**
         * Overrides all headers with the given map.
         * @param headers a map of headers to replace any existing headers
         * @return the Builder instance for chaining
         */
        public Builder withHeaders(Map<String, String> headers) {
            this.headers = new HashMap<>(headers); // Create a copy to avoid external modifications
            return this;
        }

        /**
         * Builds and returns the HttpResponse instance.
         * @return a new HttpResponse instance
         */
        public HttpResponse build() {
            return new HttpResponse(this);
        }
    }
}
