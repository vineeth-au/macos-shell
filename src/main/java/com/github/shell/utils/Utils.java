package com.github.shell.utils;

public final class Utils {
  private Utils () {
    throw new IllegalStateException("Util Class not to be instantiated!!!");
  }

  public static final String EXIT_COMMAND = "EXIT";
  public static final String ECHO_COMMAND = "ECHO";

  public static boolean IS_SHELL_COMMAND(final String shellCommand) {
    return shellCommand.equalsIgnoreCase(EXIT_COMMAND)
        || shellCommand.equalsIgnoreCase(ECHO_COMMAND);
  }
}
