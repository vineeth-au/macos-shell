package com.github.shell.io;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Commands {

  private final Scanner scanner;
  private final Logger log = LoggerFactory.getLogger(Commands.class);

  public Commands(final Scanner scanner) {
    this.scanner = scanner;
  }

  public void startInputController() {
    try {
      while (true) {
        System.out.println("$");
        String command = readInput();
        evaluateCommand(command);
      }
    } finally {
      Runtime.getRuntime().addShutdownHook(new Thread(scanner::close));
    }
  }

  private String readInput() {
    String lineToRead = "";
    try {
      lineToRead = scanner.nextLine();
    } catch (InputMismatchException inputMismatch) {
      log.info("InputMismatch " + inputMismatch.getMessage());
    } catch (Exception exception) {
      log.error("Error " + exception.getMessage());
    }
    return lineToRead;
  }

  private void evaluateCommand(final String command) {
    System.out.println(command + ": " + "command not found");
  }
}
