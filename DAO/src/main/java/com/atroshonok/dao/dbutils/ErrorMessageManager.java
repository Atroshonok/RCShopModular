/**
 * 
 */
package com.atroshonok.dao.dbutils;

import java.util.ResourceBundle;

/**
 * @author Ivan Atroshonok
 *
 */
public class ErrorMessageManager {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("error.messages");

    private ErrorMessageManager() {
    }

    public static String getProperty(String key) {
      return resourceBundle.getString(key);
    }

}
