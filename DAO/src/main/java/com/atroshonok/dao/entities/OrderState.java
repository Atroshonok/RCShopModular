/**
 * 
 */
package com.atroshonok.dao.entities;

/**
 * @author Atroshonok Ivan
 *
 */
public enum OrderState {

	// as I understood all orders could be only in OPEN state?
	// and PROCESSING and PROCESSED are never used
	// if so please remove them
	// but then if only 1 status is present we need to think if we need OrderState at all
	PROCESSING,
	PROCESSED,
	OPEN
}
