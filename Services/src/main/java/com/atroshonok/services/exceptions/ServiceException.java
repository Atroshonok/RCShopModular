package com.atroshonok.services.exceptions;

public class ServiceException extends Exception {

    private static final long serialVersionUID = 5377600129457598688L;

    public ServiceException() {
	super();
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @param message
     * @param cause
     */
    public ServiceException(String message, Throwable cause) {
	super(message, cause);
    }

    /**
     * @param message
     */
    public ServiceException(String message) {
	super(message);
    }

    /**
     * @param cause
     */
    public ServiceException(Throwable cause) {
	super(cause);
    }
    

    
}
