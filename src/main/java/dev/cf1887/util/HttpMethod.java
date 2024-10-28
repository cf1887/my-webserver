package dev.cf1887.util;

/**
 * Enum of supported http request methods.
 * GET and HEAD must be supported by general-purpose servers.
 * 
 * @link https://datatracker.ietf.org/doc/html/rfc7231#section-4.1
 */
public enum HttpMethod {
    GET,
    HEAD,
    // POST,
    // PUT,
    // DELETE,
    // CONNECT,
    // OPTIONS,
    // TRACE,
}
