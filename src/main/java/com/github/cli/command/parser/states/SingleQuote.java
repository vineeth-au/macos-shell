package com.github.cli.command.parser.states;

import static com.github.cli.utils.ConsoleUtils.SINGLE_QUOTES;

import com.github.cli.command.parser.ParserContext;

public final class SingleQuote implements ParseState {

  public static final SingleQuote INSTANCE = new SingleQuote();

  private SingleQuote() {
  }

  @Override
  public void handle(char current, ParserContext context) {
    if (current == SINGLE_QUOTES) {
      context.setParserState(NormalState.INSTANCE);
    } else {
      context.appendChar(current);
    }
  }
}
