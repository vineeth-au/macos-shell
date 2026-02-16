package com.github.cli.commands;

import static com.github.cli.utils.ConsoleUtils.USER_DIRECTORY;

public class PrintWorkingDirectory implements Command {

  @Override
  public void execute(String builtIn, String argument) {
    System.out.println(System.getProperty(USER_DIRECTORY));
  }
}
