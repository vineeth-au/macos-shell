package com.github.shell.io;

import static com.github.shell.utils.Utils.COMMAND_IS;
import static com.github.shell.utils.Utils.ECHO_COMMAND;
import static com.github.shell.utils.Utils.EXIT_COMMAND;
import static com.github.shell.utils.Utils.TYPE_COMMAND;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Commands {

  private volatile boolean IS_RUNNING = true;
  private final Logger log = LoggerFactory.getLogger(Commands.class);

  public void startInputController() {
    try (Scanner scanner = new Scanner(System.in)){
      while (IS_RUNNING) {
        String commandToExecute = readInput(scanner);
        if (Objects.nonNull(commandToExecute)) {
          evaluateCommand(commandToExecute);
        }
      }
    }
  }

  public void stop() {
    IS_RUNNING = false;
  }

  private String readInput(final Scanner scanner) {
    System.out.print("$ ");
    String lineToRead = "";
    try {
      lineToRead = scanner.nextLine().stripTrailing();
    } catch (NoSuchElementException noSuchElement) {
      log.info("InputMismatch {}", noSuchElement.getMessage());
    } catch (IllegalStateException illegalState) {
      log.error("Error {}", illegalState.getMessage());
    }
    return lineToRead;
  }

  private void evaluateCommand(final String command) {
    String builtIn = getBuiltIn(command);

    if (COMMAND_IS(builtIn)) {
      switch (builtIn.toUpperCase()) {
        case ECHO_COMMAND -> echoCommand(command);
        case EXIT_COMMAND -> exitCommand();
        case TYPE_COMMAND -> typeCommand(command);
      }
    } else {
      System.out.println(command + ": " + "command not found");
    }
  }

  private void typeCommand(String command) {
    System.out.println(getArgument(command)+" is a shell builtin");
  }

  private void exitCommand() {
    System.exit(0);
  }

  private void echoCommand(String command) {
    System.out.println(getArgument(command));
  }

  private String getBuiltIn(String command) {
    int firstSpaceIndex = command.indexOf(" ");
    if (firstSpaceIndex == -1) {
      return command;
    } else {
      return command.substring(0, firstSpaceIndex);
    }
  }

  private String getArgument(String command) {
    int firstSpaceIndex = command.indexOf(" ");
    if (firstSpaceIndex == -1) {
      return command;
    } else {
      return command.substring(firstSpaceIndex + 1);
    }
  }
}
