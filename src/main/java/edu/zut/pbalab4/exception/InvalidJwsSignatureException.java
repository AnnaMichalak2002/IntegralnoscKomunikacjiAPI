package edu.zut.pbalab4.exception;

public class InvalidJwsSignatureException extends RuntimeException {
    public InvalidJwsSignatureException() {
        super("JWS integrity verification failed");
    }
}