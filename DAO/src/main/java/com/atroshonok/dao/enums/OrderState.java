/**
 * 
 */
package com.atroshonok.dao.enums;

/**
 * @author Atroshonok Ivan
 *
 */
public enum OrderState {
	
	// as I understood all orders could be only in OPEN state?
	// and PROCESSING and PROCESSED are never used
	// if so please remove them
	// but then if only 1 status is present we need to think if we need OrderState at all
	// i have not implemented the using this 
	
	// i have not implemented the using all of this states.
	// but i think i will need them in the future.

    PROCESSING, PROCESSED, OPEN
}
