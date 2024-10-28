package dev.cf1887.util;

/**
 * Enum representing HTTP status codes and their default reason phrases.
 */
public enum HttpStatus {
    OK(200, "OK"),
    CREATED(201, "Created"),
    NO_CONTENT(204, "No Content"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    NOT_IMPLEMENTED(501, "Not Implemented");

    private final int code;
    private final String reasonPhrase;

    /**
     * Constructor for HttpStatus enum.
     * 
     * @param code         the HTTP status code
     * @param reasonPhrase the default reason phrase associated with the status code
     */
    HttpStatus(int code, String reasonPhrase) {
        this.code = code;
        this.reasonPhrase = reasonPhrase;
    }

    /**
     * Gets the HTTP status code.
     * 
     * @return the status code as an integer
     */
    public int getCode() {
        return code;
    }

    /**
     * Gets the reason phrase associated with the HTTP status code.
     * 
     * @return the reason phrase as a String
     */
    public String getReasonPhrase() {
        return reasonPhrase;
    }

    /**
     * Retrieves an HttpStatus by code.
     * 
     * @param code the HTTP status code to lookup
     * @return the corresponding HttpStatus, or null if not found
     */
    public static HttpStatus fromCode(int code) {
        for (HttpStatus status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null; // Or throw an exception if code not supported
    }
}
