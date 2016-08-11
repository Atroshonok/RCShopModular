/**
 * 
 */
package com.atroshonok.services.exceptions;

/**
 * @author Atroshonok Ivan
 *
 */
public class ErrorUpdatingPoductServiceException extends Exception {

	private static final long serialVersionUID = 7908305365191385412L;

	public ErrorUpdatingPoductServiceException() {
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ErrorUpdatingPoductServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ErrorUpdatingPoductServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ErrorUpdatingPoductServiceException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ErrorUpdatingPoductServiceException(Throwable cause) {
		super(cause);
	}
	
	

}
