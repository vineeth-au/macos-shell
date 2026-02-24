package com.github.cli.commands;

import static com.github.cli.utils.ConsoleUtils.SINGLE_QUOTE;

public final class Echo implements Command {

  @Override
  public void execute(String builtIn, String argument) {
    String arg = getArgumentFrom(argument);
    if (!containsQuotes(arg)) {
      System.out.println(normalizeSpace(arg));
    } else {
      System.out.println(printStringInsideQuotes(arg));
    }
  }

  private boolean containsQuotes(final String argument) {
    return argument.contains(SINGLE_QUOTE);
  }

  private String normalizeSpace(String argument) {
    return argument.strip().replaceAll("\\s+", " ");
  }

  public String printStringInsideQuotes(String argument) {
    argument = argument.replaceAll("''", "");
    int numberOfQuotes = getNumberOfSingleQuotes(argument);
    if (numberOfQuotes == 0) {
      return argument;
    } else if (numberOfQuotes == 2) {
      return argument
          .replaceAll("''", "")
          .substring(1, argument.length() - 1);
    } else {
      return argument.replaceAll("'", "");
    }
  }

  private int getNumberOfSingleQuotes(final String argument) {
    return Math.toIntExact(argument.chars().filter(e -> e == '\'').count());
  }

}
