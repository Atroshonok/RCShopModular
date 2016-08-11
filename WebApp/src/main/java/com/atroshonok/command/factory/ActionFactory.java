/**
 * 
 */
package com.atroshonok.command.factory;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.atroshonok.command.ActionCommand;
import com.atroshonok.command.CommandEnum;
import com.atroshonok.command.EmptyCommand;

/**
 * @author Atroshonok Ivan
 *
 */
public class ActionFactory {
	private Logger log = Logger.getLogger(getClass());
	
	public ActionCommand defineCommand(HttpServletRequest request) {
		ActionCommand current = new EmptyCommand();
		
		// getting the command name from request
		String action = request.getParameter("command");
		
		log.debug("Command name from request: " + action);
		
		// if a command is empty in this request
		if (action == null || action.isEmpty()) {
			return current;
		}
		// getting a object that coresponds to a command
		try {
			CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());

			current = currentEnum.getCurrentCommand();
			
		} catch (IllegalArgumentException e) {
			log.error("So command does't exist: " + e);
		}
		return current;
	}
}
