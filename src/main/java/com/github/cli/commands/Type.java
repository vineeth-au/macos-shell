package com.github.cli.commands;

import static com.github.cli.utils.ConsoleUtils.IS_COMMAND_BUILTIN;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Type implements Command {

  private final Logger log = LoggerFactory.getLogger(Type.class);

  @Override
  public void execute(String builtIn, String command) {
    String argument = getArgumentFrom(command);
    boolean isBuiltInCommand = isBuiltInCommand(argument);
    if (!isBuiltInCommand) {
      locateExecutableFile(argument);
    }
  }

  private void locateExecutableFile(String argument) {
    String validCommand = "";
    for (String eachFilePath : getSystemEnvironmentPaths()) {
      validCommand = checkEachFilePath(eachFilePath, argument);
      if (!validCommand.isEmpty()) {
        System.out.println(argument + " is " + eachFilePath + "/" + argument);
        break;
      }
    }
    if (validCommand.isEmpty()) {
      System.out.println(argument + ": not found");
    }
  }

  private boolean isBuiltInCommand(String argument) {
    if (IS_COMMAND_BUILTIN(argument)) {
      System.out.println(argument + " is a shell builtin");
      return true;
    }
    return false;
  }
}
