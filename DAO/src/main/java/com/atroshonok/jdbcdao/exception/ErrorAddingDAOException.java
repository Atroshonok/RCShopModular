/**
 * 
 */
package com.atroshonok.jdbcdao.exception;

/**
 * @author Atroshonok Ivan
 *
 */
public class ErrorAddingDAOException extends Exception {

	private static final long serialVersionUID = 5036047258749002889L;

	public ErrorAddingDAOException() {
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ErrorAddingDAOException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ErrorAddingDAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ErrorAddingDAOException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ErrorAddingDAOException(Throwable cause) {
		super(cause);
	}
	
	

}
