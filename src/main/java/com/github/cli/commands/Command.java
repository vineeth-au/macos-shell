package com.github.cli.commands;

public interface Command {

  void execute(final String builtIn, final String argument);

  default String getBuiltInFrom(final String command) {
    int firstSpaceIndex = command.indexOf(" ");
    if (firstSpaceIndex == -1) {
      return command;
    } else {
      return command.substring(0, firstSpaceIndex);
    }
  }

  default String getArgumentFrom(final String command) {
    int firstSpaceIndex = command.indexOf(" ");
    if (firstSpaceIndex == -1) {
      return command;
    } else {
      return command.substring(firstSpaceIndex + 1);
    }
  }
}
