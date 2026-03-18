package com.github.cli.command.parser;

import static com.github.cli.utils.ConsoleUtils.SPACE;

import com.github.cli.command.parser.states.NormalState;
import com.github.cli.command.parser.states.ParseState;
import lombok.Getter;

public final class ParserContext {

  @Getter
  private final String input;
  @Getter
  private int position;
  private final StringBuilder output;
  private ParseState parseState;

  public ParserContext(final String input) {
    this.input = input;
    this.output = new StringBuilder();
    this.position = 0;
    this.parseState = NormalState.INSTANCE;
  }

  public boolean hasNext() {
    return position < input.length();
  }

  public char getCurrentChar() {
    return input.charAt(position);
  }

  public boolean lastCharWasSpace() {
    int len = output.length();
    return len > 0 && output.charAt(len - 1) == SPACE;
  }

  public void increment() {
    position++;
  }

  public void appendChar(char c) {
    output.append(c);
  }

  public String getOutputAsString() {
    return output.toString();
  }

  public void clearOutput() {
    output.setLength(0);
  }

  public ParseState getState() {
    return parseState;
  }

  public void setParserState(ParseState state) {
    if (this.parseState != state) {
      this.parseState = state;
    }
  }
}
