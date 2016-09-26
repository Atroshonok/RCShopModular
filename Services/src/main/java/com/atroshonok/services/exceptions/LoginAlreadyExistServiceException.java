package com.atroshonok.services.exceptions;

public class LoginAlreadyExistServiceException extends Exception {

    private static final long serialVersionUID = -429757055143148774L;

    /**
     * 
     */
    public LoginAlreadyExistServiceException() {
	super();
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public LoginAlreadyExistServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @param message
     * @param cause
     */
    public LoginAlreadyExistServiceException(String message, Throwable cause) {
	super(message, cause);
    }

    /**
     * @param message
     */
    public LoginAlreadyExistServiceException(String message) {
	super(message);
    }

    /**
     * @param cause
     */
    public LoginAlreadyExistServiceException(Throwable cause) {
	super(cause);
    }

}
