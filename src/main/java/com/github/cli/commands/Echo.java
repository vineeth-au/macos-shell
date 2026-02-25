package com.github.cli.commands;

import static com.github.cli.utils.ConsoleUtils.SINGLE_QUOTE;

public final class Echo implements Command {

  @Override
  public void execute(String builtIn, String argument) {
    final String arg = getArgumentFrom(argument);
    if (!containsQuotes(arg)) {
      normalizeSpace(arg);
    } else {
      printStringInsideQuotes(arg);
    }
  }

  private boolean containsQuotes(String argument) {
    return argument.contains(SINGLE_QUOTE);
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
