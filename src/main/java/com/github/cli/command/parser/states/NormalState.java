package com.github.cli.command.parser.states;

import static com.github.cli.utils.ConsoleUtils.BACKSLASH;
import static com.github.cli.utils.ConsoleUtils.DOUBLE_QUOTES;
import static com.github.cli.utils.ConsoleUtils.SINGLE_QUOTES;
import static com.github.cli.utils.ConsoleUtils.SPACE;

import com.github.cli.command.parser.ParserContext;

public final class NormalState implements ParseState {

  public static final NormalState INSTANCE = new NormalState();

  private NormalState() {
  }

  @Override
  public void handle(char current, ParserContext context) {
    switch (current) {
      case SINGLE_QUOTES -> context.setParserState(SingleQuote.INSTANCE);
      case DOUBLE_QUOTES -> context.setParserState(DoubleQuote.INSTANCE);
      case BACKSLASH -> context.setParserState(BackSlash.INSTANCE);
      case SPACE -> {
        if (!context.lastCharWasSpace()) {
          context.appendChar(SPACE);
        }
      }
      default -> context.appendChar(current);
    }
  }
}
