package com.github.cli.command;

@FunctionalInterface
public interface Command {

  void execute(final String command);

}
