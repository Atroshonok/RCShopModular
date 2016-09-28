package com.atroshonok.utilits;

import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

/**
 * Class takes an information from messages.properties file
 */

// класс извлекает информацию из файла messages.properties
public class MessageManager {
  private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");
  
  private MessageManager() {
  }

  public static String getProperty(String key) {
    return resourceBundle.getString(key);
  }
  
}