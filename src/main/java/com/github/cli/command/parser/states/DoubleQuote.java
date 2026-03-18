package com.github.cli.command.parser.states;

import static com.github.cli.utils.ConsoleUtils.BACKSLASH;
import static com.github.cli.utils.ConsoleUtils.DOUBLE_QUOTES;

import com.github.cli.command.parser.ParserContext;

public final class DoubleQuote implements ParseState{
  public static final DoubleQuote INSTANCE = new DoubleQuote();

  private DoubleQuote() {
  }
  @Override
  public void handle(char current, ParserContext context) {
    if (current == DOUBLE_QUOTES) {
      context.setParserState(NormalState.INSTANCE);
    } else if (current == BACKSLASH) {
      context.setParserState(BackSlash.INSTANCE);
    } else {
      context.appendChar(current);
    }
  }
}
