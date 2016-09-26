package com.atroshonok.utilits;

import java.util.ResourceBundle;
/**
* Class takes an information from config.properties file
*/
public class AdminPageConfigManager {
  private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("admin_config");

  private AdminPageConfigManager() {
  }

  public static String getProperty(String key) {
    return resourceBundle.getString(key);
  }
}