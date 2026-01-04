package de.htw_berlin.studymatch.exceptions;

public class UserAlreadyLikedException extends RuntimeException {
    public UserAlreadyLikedException(String message) {
        super(message);
    }
}
