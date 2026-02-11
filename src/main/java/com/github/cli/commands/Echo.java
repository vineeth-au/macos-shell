package com.github.cli.commands;

public final class Echo implements Command {

  @Override
  public void execute(String builtIn, String argument) {
    System.out.println(getArgumentFrom(argument));
  }
}
