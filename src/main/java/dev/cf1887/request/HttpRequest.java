package dev.cf1887.request;

import java.util.Map;

import dev.cf1887.util.HttpMethod;

public class HttpRequest {

    private HttpMethod method;
    private String path;
    private String version;
    private Map<String, String> headers;
    private String body;

    /**
     * Constructor
     * @param method method of the incoming request
     * @param path requested uri
     * @param version http version
     * @param headers key-value pairs of information
     * @param body content sent within the request body
     */
    public HttpRequest(HttpMethod method, String path, String version, Map<String, String> headers, String body) {
        this.method = method;
        this.path = path;
        this.version = version;
        this.headers = headers;
        this.body = body;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }
}
