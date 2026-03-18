package com.github.cli.command.commands;

import static com.github.cli.utils.ConsoleUtils.USER_DIRECTORY;

import com.github.cli.command.Command;

public class PrintWorkingDirectory implements Command {

  @Override
  public void execute(String command) {
    System.out.println(System.getProperty(USER_DIRECTORY));
  }
}
