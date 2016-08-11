/**
 * 
 */
package com.atroshonok.dao.exception;

/**
 * @author Atroshonok Ivan
 *
 */
public class NoExistUserDAOException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3677160471286049864L;

	/**
	 * 
	 */
	public NoExistUserDAOException() {
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public NoExistUserDAOException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NoExistUserDAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public NoExistUserDAOException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public NoExistUserDAOException(Throwable cause) {
		super(cause);
	}
	
	

}
