package com.github.cli.commands;

import static com.github.cli.utils.ConsoleUtils.COMMAND_IS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Type implements Command {

  private final Logger log = LoggerFactory.getLogger(Type.class);

  @Override
  public void execute(String builtIn, String argument) {
    String isBuiltIn = getArgument(argument);
    if (COMMAND_IS(isBuiltIn)) {
      System.out.println(getArgument(argument) + " is a shell builtin");
    } else {
      System.out.println(isBuiltIn + ": not found");
    }
  }
}
