/**
 * 
 */
package com.atroshonok.services.exceptions;

/**
 * @author Atroshonok Ivan
 *
 */
public class ErrorSavingOrderServiceException extends Exception {

	private static final long serialVersionUID = 7469021175020664852L;

	/**
	 * 
	 */
	public ErrorSavingOrderServiceException() {
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ErrorSavingOrderServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ErrorSavingOrderServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ErrorSavingOrderServiceException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ErrorSavingOrderServiceException(Throwable cause) {
		super(cause);
	}
	
	

}
