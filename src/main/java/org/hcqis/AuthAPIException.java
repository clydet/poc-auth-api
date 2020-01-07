package org.hcqis;

public class AuthAPIException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public AuthAPIException(String message, Exception e) {
        super(message, e);
    }
}