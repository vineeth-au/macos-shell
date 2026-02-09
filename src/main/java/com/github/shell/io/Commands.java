package com.github.shell.io;

import static com.github.shell.utils.Utils.EXIT_COMMAND;

import com.github.shell.utils.Utils;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Commands {

  private volatile boolean IS_RUNNING = true;
  private final Scanner scanner;
  private final Logger log = LoggerFactory.getLogger(Commands.class);

  public Commands(final Scanner scanner) {
    this.scanner = scanner;
  }

  public void startInputController() {
    try {
      while (IS_RUNNING) {
        System.out.print("$ ");
        String commandToExecute = readInput();
        if (commandToExecute != null) {
          evaluateCommand(commandToExecute);
        }
      }
    } finally {
      scanner.close();
      System.exit(0);
    }
  }

  public void stop() {
    IS_RUNNING = false;
  }

  private String readInput() {
    String lineToRead = "";
    try {
      lineToRead = scanner.nextLine();
      if (lineToRead.isEmpty()) {
        //TODO: Maybe something in future?
        return "";
      }
    } catch (InputMismatchException inputMismatch) {
      log.info("InputMismatch {}", inputMismatch.getMessage());
    } catch (Exception exception) {
      log.error("Error {}", exception.getMessage());
    }
    return lineToRead;
  }

  private void evaluateCommand(final String command) {
    if (command.equalsIgnoreCase(EXIT_COMMAND)) {
      exitApplication();
    }
    System.out.println(command + ": " + "command not found");
  }

  private void exitApplication() {
    scanner.close();
    System.exit(0);
  }
}
