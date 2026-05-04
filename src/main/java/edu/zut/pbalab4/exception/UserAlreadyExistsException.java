package edu.zut.pbalab4.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String personalId) {
        super("User with personalId " + personalId + " already exists");
    }
}