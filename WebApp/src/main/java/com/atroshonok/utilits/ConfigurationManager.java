package com.atroshonok.utilits;

import java.util.ResourceBundle;
/**
* Class takes an information from config.properties file
*/
public class ConfigurationManager {
  private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("config");

  private ConfigurationManager() {
  }

  public static String getProperty(String key) {
    return resourceBundle.getString(key);
  }
}