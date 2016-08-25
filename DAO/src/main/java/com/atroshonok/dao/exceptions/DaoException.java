package com.atroshonok.dao.exceptions;

public class DaoException extends Exception {

    private static final long serialVersionUID = -4602088348418661671L;
    
    private Exception exception;

    public DaoException(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
