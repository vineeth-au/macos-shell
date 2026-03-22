package com.github.cli.command.parser;

import static com.github.cli.utils.ConsoleUtils.DOUBLE;
import static com.github.cli.utils.ConsoleUtils.ESCAPE;
import static com.github.cli.utils.ConsoleUtils.SINGLE;
import static com.github.cli.utils.ConsoleUtils.WHITESPACE;

public enum ParseState {
  NORMAL {
    @Override
    public void handle(char current, ParserContext context) {
      switch (current) {
        case SINGLE -> context.setParserState(SINGLE_QUOTE);
        case DOUBLE -> context.setParserState(DOUBLE_QUOTE);
        case ESCAPE -> context.setParserState(BACKSLASH);
        case WHITESPACE -> {
          if (!context.lastCharWasSpace()) {
            context.appendChar(WHITESPACE);
          }
        }
        default -> context.appendChar(current);
      }
    }
  }, SINGLE_QUOTE {
    @Override
    public void handle(char current, ParserContext context) {
      if (current == SINGLE) {
        context.setParserState(NORMAL);
      } else {
        context.appendChar(current);
      }
    }
  }, DOUBLE_QUOTE {
    @Override
    public void handle(char current, ParserContext context) {
      if (current == DOUBLE) {
        context.setParserState(NORMAL);
      } else if (current == ESCAPE) {
        context.setParserState(BACKSLASH);
      } else {
        context.appendChar(current);
      }
    }
  }, BACKSLASH {
    @Override
    public void handle(char current, ParserContext context) {
      context.appendChar(current);
      context.setParserState(NORMAL);
    }
  };

  abstract void handle(char current, ParserContext context);
}
