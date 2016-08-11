/**
 * 
 */
package com.atroshonok.services.exceptions;

/**
 * @author Atroshonok Ivan
 *
 */
public class ErrorAddingUserServiceException extends Exception {

	private static final long serialVersionUID = 7355025785212026015L;

	public ErrorAddingUserServiceException() {
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ErrorAddingUserServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ErrorAddingUserServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ErrorAddingUserServiceException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ErrorAddingUserServiceException(Throwable cause) {
		super(cause);
	}
	
	

}
