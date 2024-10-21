package dev.cf1887.exception;

public class InvalidRequestLineException extends Exception {
    
    public InvalidRequestLineException() {
        super("The given request line is invalid");
    }
}
