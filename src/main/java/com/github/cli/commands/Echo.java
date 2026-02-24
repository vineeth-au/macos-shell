package com.github.cli.commands;

import static com.github.cli.utils.ConsoleUtils.SINGLE_QUOTE;

import java.util.HashMap;
import java.util.Map;

public final class Echo implements Command {

  Map<Integer, Integer> quoteIndexLocation = new HashMap<>();

  @Override
  public void execute(String builtIn, String argument) {
    String arg = getArgumentFrom(argument);
    if (!containsQuotes(arg)) {
      normalizeSpace(arg);
    } else {
      printStringInsideQuotes(arg);
    }
  }

  private boolean containsQuotes(final String argument) {
    return argument.contains(SINGLE_QUOTE);
  }

  private void normalizeSpace(String argument) {
    argument = argument.strip().replaceAll("\\s+", " ");
    System.out.println(argument);
  }

  private void printStringInsideQuotes(String argument) {
    calculateTotalNumberOfQuotes(argument);

    int totalNumberOfQuotes = quoteIndexLocation.size();
    if (totalNumberOfQuotes == 2) {
      int firstQuoteIndex = quoteIndexLocation.get(1);
      int secondQuoteIndex = quoteIndexLocation.get(2);
      if (firstQuoteIndex + 1 == secondQuoteIndex) {
        argument = argument.replaceAll("''", "");
        System.out.println(argument);
      } else {
        System.out.println(argument.substring(1, argument.length() - 1));
      }
      return;
    } else {
      argument = argument.replaceAll("''", "");
      System.out.println(argument.substring(1, argument.length() - 1));
    }

  }

  private void calculateTotalNumberOfQuotes(String argument) {
    int counter = 0;
    for (int i = 0; i < argument.length(); i++) {
      if (argument.charAt(i) == '\'') {
        counter++;
        quoteIndexLocation.put(counter, i);
      }
    }
  }
}
