package com.atroshonok.utilits;

import java.util.ResourceBundle;

/**
 * Class takes an information from messages.properties file
 */
public class MessageManager {
  private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

  // класс извлекает информацию из файла messages. properties
  private MessageManager() {
  }

  public static String getProperty(String key) {
    return resourceBundle.getString(key);
  }
}