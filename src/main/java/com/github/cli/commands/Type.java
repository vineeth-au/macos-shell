package com.github.cli.commands;

import static com.github.cli.utils.ConsoleUtils.isCommandBuiltin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

public final class Type implements Command {

  @Getter
  private final Map<String, String> validFileMap;
  private final List<String> environmentPaths;

  public Type() {
    environmentPaths = List.copyOf(getSystemEnvironmentPaths());
    validFileMap = new HashMap<>();
  }

  @Override
  public void execute(String builtIn, String command) {
    String argument = getArgumentFrom(command);
    if (!isBuiltInCommand(argument)) {
      findExecutable(argument);
    }
  }

  private void findExecutable(final String argument) {
    if (!getValidFileMap().isEmpty() && getValidFileMap().containsKey(argument)) {
      String filePath = getValidFileMap().get(argument);
      System.out.println(argument + " is " + filePath + "/" + argument);
      return;
    }
    String validCommand = "";
    for (String eachFilePath : environmentPaths) {
      validCommand = checkEachFilePath(eachFilePath, argument);
      if (!validCommand.isEmpty()) {
        getValidFileMap().put(argument, eachFilePath);
        System.out.println(argument + " is " + eachFilePath + "/" + argument);
        break;
      }
    }
    if (validCommand.isEmpty()) {
      System.out.println(argument + ": not found");
    }
  }

  private boolean isBuiltInCommand(String argument) {
    if (isCommandBuiltin(argument)) {
      System.out.println(argument + " is a shell builtin");
      return true;
    }
    return false;
  }
}
