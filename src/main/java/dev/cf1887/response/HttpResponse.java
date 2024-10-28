package dev.cf1887.response;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import dev.cf1887.util.HttpStatus;

public class HttpResponse {

    // Constant map for default headers
    private static final Map<String, String> HEADERS_DEFAULT;
    static {
        Map<String, String> buffer = new HashMap<>();
        buffer.put("Content-Type", "text/html; charset=UTF-8");
        HEADERS_DEFAULT = Collections.unmodifiableMap(buffer);
    }
    // Default values for HTTP version and status
    private static final String DEFAULT_HTTP_VERSION = "HTTP/1.1";
    private static final HttpStatus DEFAULT_STATUS = HttpStatus.OK;

    // Properties
    private final Map<String, String> headers;
    private final String content;
    private final String httpVersion;
    private final HttpStatus status;

    /**
     * Private constructor for builder pattern usage
     * 
     * @param builder The Builder instance configuring this HttpResponse
     */
    private HttpResponse(Builder builder) {
        this.headers = new HashMap<>(builder.headers == null ? HEADERS_DEFAULT : builder.headers);
        this.content = builder.content;
        this.httpVersion = builder.httpVersion == null ? DEFAULT_HTTP_VERSION : builder.httpVersion;
        this.status = builder.status == null ? DEFAULT_STATUS : builder.status;
    }

    /**
     * Getter for headers (unmodifiable view)
     * 
     * @return an unmodifiable map of headers
     */
    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(this.headers);
    }

    /**
     * Returns the content of the response
     * 
     * @return the response content as a String
     */
    public String getContent() {
        return this.content;
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
        // Start with the HTTP status line including version, status code, and reason
        // phrase
        responseBuilder.append(httpVersion).append(" ").append(status.getCode())
                .append(" ").append(status.getReasonPhrase())
                .append(System.lineSeparator());
        // Append each header as "Key: Value" followed by a newline
        for (Map.Entry<String, String> header : headers.entrySet()) {
            responseBuilder.append(header.getKey()).append(": ").append(header.getValue())
                    .append(System.lineSeparator());
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
     * Static builder class for using the builder pattern
     */
    public static class Builder {
        // Properties to be built
        private Map<String, String> headers = null;
        private String content = "";
        private String httpVersion = null;
        private HttpStatus status = null;

        public Builder() {
        }

        /**
         * Sets the content
         * 
         * @param content the response content
         * @return the Builder instance for chaining
         */
        public Builder withContent(String content) {
            this.content = content;
            return this;
        }

        /**
         * Adds one additional header to the map of headers
         * 
         * @param key   the header name
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
         * 
         * @param headers a map of headers to replace any existing headers
         * @return the Builder instance for chaining
         */
        public Builder withHeaders(Map<String, String> headers) {
            this.headers = new HashMap<>(headers); // Create a copy to avoid external modifications
            return this;
        }

        /**
         * Sets the HTTP version.
         * 
         * @param httpVersion the HTTP version string (e.g., "HTTP/1.1")
         * @return the Builder instance for chaining
         */
        public Builder withHttpVersion(String httpVersion) {
            this.httpVersion = httpVersion;
            return this;
        }

        /**
         * Sets the HTTP status using an HttpStatus enum.
         * 
         * @param status the HTTP status enum (e.g., HttpStatus.OK)
         * @return the Builder instance for chaining
         */
        public Builder withStatus(HttpStatus status) {
            this.status = status;
            return this;
        }

        /**
         * Builds and returns the HttpResponse instance.
         * 
         * @return a new HttpResponse instance
         */
        public HttpResponse build() {
            return new HttpResponse(this);
        }
    }
}
