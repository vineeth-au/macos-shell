package com.github.cli.utils;

public final class ConsoleUtils {

  private ConsoleUtils() {
    throw new IllegalStateException("Util Class not to be instantiated!!!");
  }

  public static final String EXIT_COMMAND = "EXIT";
  public static final String ECHO_COMMAND = "ECHO";
  public static final String TYPE_COMMAND = "TYPE";
  public static final String PATH = "PATH";

  public static boolean COMMAND_IS(final String shellCommand) {
    return shellCommand.equalsIgnoreCase(EXIT_COMMAND)
        || shellCommand.equalsIgnoreCase(ECHO_COMMAND)
        || shellCommand.equalsIgnoreCase(TYPE_COMMAND);
  }
}
