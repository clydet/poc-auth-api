package org.hcqis.jwt;

public class JWTManagerException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public JWTManagerException(String message, Exception e) {
        super(message, e);
    }
}