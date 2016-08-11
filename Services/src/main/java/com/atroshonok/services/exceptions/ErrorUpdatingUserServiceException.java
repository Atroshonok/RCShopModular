/**
 * 
 */
package com.atroshonok.services.exceptions;

/**
 * @author Atroshonok Ivan
 *
 */
public class ErrorUpdatingUserServiceException extends Exception {

	private static final long serialVersionUID = -7591233451493408144L;

	/**
	 * 
	 */
	public ErrorUpdatingUserServiceException() {
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ErrorUpdatingUserServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ErrorUpdatingUserServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ErrorUpdatingUserServiceException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ErrorUpdatingUserServiceException(Throwable cause) {
		super(cause);
	}

	
}
