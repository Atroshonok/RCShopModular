/**
 * 
 */
package com.atroshonok.jdbcdao.dbconectutils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * @author Atroshonok Ivan
 *
 */
public class ConnectionPool {
	private static DataSource dataSource;
	private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
	private static Logger log = Logger.getLogger(ConnectionPool.class.getName());
	
	static {
		try {
			Context initContext = new InitialContext();
			dataSource = (DataSource) initContext.lookup("java:comp/env/" + getProperty("name"));
		} catch (NamingException e) {
			log.error("Incorrect configurating DataSource: " + e);
		}
	}
	
	private ConnectionPool() {
	}
	
	public static Connection getConnection() throws SQLException {
//	    	Connection connection = ConnectionManager.getInstance().createConnection();
		Connection connection = dataSource.getConnection();
		return connection;
	}
	
	public static void releaseConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				log.error("Error releasing connection: " + e);
			}
		}
	}
	
	private static String getProperty(String key) {
		return resourceBundle.getString(key);
	}

}
