package com.github.cli.utils;

public final class ConsoleUtils {

  private ConsoleUtils() {
    throw new IllegalStateException("Util Class not to be instantiated!!!");
  }

  public static final String EXIT_COMMAND = "EXIT";
  public static final String ECHO_COMMAND = "ECHO";
  public static final String TYPE_COMMAND = "TYPE";
  public static final String PWD_COMMAND = "PWD";
  public static final String CD_COMMAND = "CD";

  public static final String PATH = "PATH";
  public static final String CURRENT_USER_DIRECTORY = "user.dir";

  public static boolean isCommandBuiltin(final String shellCommand) {
    return shellCommand.equalsIgnoreCase(EXIT_COMMAND)
        || shellCommand.equalsIgnoreCase(ECHO_COMMAND)
        || shellCommand.equalsIgnoreCase(TYPE_COMMAND)
        || shellCommand.equalsIgnoreCase(PWD_COMMAND)
        || shellCommand.equalsIgnoreCase(CD_COMMAND);
  }
}
