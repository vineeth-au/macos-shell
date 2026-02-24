package com.github.cli.commands;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class EchoTest {

  Echo echo = new Echo();
  private final ByteArrayOutputStream consoleContent = new ByteArrayOutputStream();
  private final PrintStream consoleOut = System.out;

  @BeforeEach
  public void setUpStreams() {
    System.setOut(new PrintStream(consoleContent));
  }

  @AfterEach
  public void restoreStreams() {
    System.setOut(consoleOut);
  }

  private static Stream<Arguments> singleQuotes() {
    return Stream.of(
        Arguments.of("'example hello'", "example hello"),
        Arguments.of("hello''world", "helloworld"),
        Arguments.of("'script     shell' 'world''example' hello''test",
            "script     shell worldexample hellotest")
    );
  }

  @ParameterizedTest
  @MethodSource("singleQuotes")
  public void singleQuotesFunctionality(String actual, String expected) {
    String output = echo.printStringInsideQuotes(actual);

    assertThat(output).isEqualTo(expected);
  }
}