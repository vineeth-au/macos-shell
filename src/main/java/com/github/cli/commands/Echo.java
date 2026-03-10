package com.github.cli.commands;

import static com.github.cli.utils.ConsoleUtils.SINGLE_QUOTE;

public final class Echo implements Command {

  @Override
  public void execute(String command) {
    final String argument = getArgumentFrom(command);
    if (!argument.contains(SINGLE_QUOTE)) {
      normalizeSpace(argument);
    } else {
      printStringInsideQuotes(argument);
    }
  }

  private void normalizeSpace(String argument) {
    System.out.println(argument.strip().replaceAll("\\s+", " "));
  }

  private void printStringInsideQuotes(String argument) {
    argument = argument.replaceAll("''", "");
    int numberOfQuotes = getNumberOfSingleQuotes(argument);
    if (numberOfQuotes == 0) {
      System.out.println(argument);
    } else if (numberOfQuotes == 2) {
      String output = argument
          .replaceAll("''", "")
          .substring(1, argument.length() - 1);
      System.out.println(output);
    } else {
      System.out.println(argument.replaceAll("'", ""));
    }
  }

  private int getNumberOfSingleQuotes(String argument) {
    return Math.toIntExact(argument.chars().filter(e -> e == '\'').count());
  }

}
