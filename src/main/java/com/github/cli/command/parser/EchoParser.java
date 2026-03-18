package com.github.cli.command.parser;

public final class EchoParser {

  public String parse(final String input) {
    ParserContext context = new ParserContext(input);

    while (context.hasNext()) {
      char current = context.getCurrentChar();
      context.getState().handle(current, context);
      context.increment();
    }
    return context.getOutputAsString();
  }
}
