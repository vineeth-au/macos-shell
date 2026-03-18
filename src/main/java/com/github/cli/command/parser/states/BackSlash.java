package com.github.cli.command.parser.states;

import com.github.cli.command.parser.ParserContext;

public final class BackSlash implements ParseState {

  public static final BackSlash INSTANCE = new BackSlash();

  private BackSlash() {
  }

  @Override
  public void handle(char current, ParserContext context) {
    context.appendChar(current);
    context.setParserState(NormalState.INSTANCE);
  }
}
