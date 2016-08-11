/**
 * 
 */
package com.atroshonok.dao.exception;

/**
 * @author Atroshonok Ivan
 *
 */
public class ErrorUpdatingDAOException extends Exception {

	private static final long serialVersionUID = 3199580489422489412L;

	/**
	 * 
	 */
	public ErrorUpdatingDAOException() {
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ErrorUpdatingDAOException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ErrorUpdatingDAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ErrorUpdatingDAOException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ErrorUpdatingDAOException(Throwable cause) {
		super(cause);
	}
	
	

}
