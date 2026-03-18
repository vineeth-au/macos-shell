package com.github.cli.command.commands;

import static com.github.cli.command.CommandUtils.getArgumentFrom;

import com.github.cli.command.Command;
import com.github.cli.command.parser.EchoParser;

public final class Echo implements Command {

  private final EchoParser echoParser;

  public Echo() {
    this.echoParser = new EchoParser();
  }

  @Override
  public void execute(String command) {
    String argument = getArgumentFrom(command);
    System.out.println(echoParser.parse(argument));
  }
}
