package com.github.cli.command.parser.states;

import com.github.cli.command.parser.ParserContext;

@FunctionalInterface
public interface ParseState {

  void handle(char current, ParserContext context);
}
