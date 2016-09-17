package com.atroshonok.dao.exceptions;

public class DaoException extends Exception {

    private static final long serialVersionUID = -4602088348418661671L;

    public DaoException() {
	super();
    }

    public DaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

    public DaoException(String message, Throwable cause) {
	super(message, cause);
    }

    public DaoException(Throwable cause) {
	super(cause);
    }

}
