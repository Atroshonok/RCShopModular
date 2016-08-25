package com.atroshonok.jdbcdao.dbconectutils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class ConnectionManager {

	private static ConnectionManager conectManager = new ConnectionManager();
	private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
	private static final String DB_PROPERTIES_FILE = "database.properties";
	private Properties properties;
	private Logger log = Logger.getLogger(getClass());

	/***/
	private ConnectionManager() {
		properties = readPropertiesFromFile();
	}
	
	public static String getProperty(String key) {
	    return resourceBundle.getString(key);
	  }

	private Properties readPropertiesFromFile() {
		Reader reader = null;
		Properties properties = new Properties();
		try {

			reader = new BufferedReader(new FileReader(DB_PROPERTIES_FILE));
			properties.load(reader);
			log.debug("DataBase properties read from file: " + properties.stringPropertyNames());

		} catch (FileNotFoundException e) {
			log.error("File " + DB_PROPERTIES_FILE + " not found! " + e);
		} catch (IOException e) {
			log.error("Error reading from file: " + DB_PROPERTIES_FILE + " - " + e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					log.error("Error closes I/O Stream: " + e);
				}
			}
		}
		return properties;
	}

	public static ConnectionManager getInstance() {
		return conectManager;
	}

	public Connection createConnection() throws SQLException {
		try {
			Class.forName(resourceBundle.getString("driver"));
			String url = resourceBundle.getString("url");
			String user = resourceBundle.getString("user");
			String password = resourceBundle.getString("password");
			
			Connection connection = DriverManager.getConnection(url, user, password);
			return (connection);
		} catch (ClassNotFoundException cnfe) {
			log.error("Error opening driver: " + cnfe);
			throw new SQLException("Can't find class for driver: " + properties.getProperty("driver"));
		}
	}

	public void closeConnection(Connection connection) {
		try {
			if (!connection.isClosed() && (connection != null)) {
				connection.close();
			}
		} catch (SQLException e) {
			log.error("Error closing connection: " + e);
		}

	}

	public void closeConnections(Collection<Connection> connections) {
		for (Connection connection : connections) {
			closeConnection(connection);
		}
	}
}
