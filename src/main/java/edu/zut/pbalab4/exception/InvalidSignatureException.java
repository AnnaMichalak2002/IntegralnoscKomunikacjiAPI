package edu.zut.pbalab4.exception;

public class InvalidSignatureException extends RuntimeException {
    public InvalidSignatureException() {
        super("Request integrity verification failed");
    }
}