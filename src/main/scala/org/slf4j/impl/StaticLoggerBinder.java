package org.slf4j.impl;

public class StaticLoggerBinder extends io.pg.OdinInterop {
  public static String REQUESTED_API_VERSION = "1.7";

  private static final StaticLoggerBinder _instance = new StaticLoggerBinder();
  public static StaticLoggerBinder getSingleton() {
    return _instance;
  }
}
