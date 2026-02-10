package com.github.cli;

import com.github.cli.commands.Command;
import com.github.cli.commands.Echo;
import com.github.cli.commands.Type;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Runner {

  private volatile boolean IS_RUNNING = true;
  private final Logger log = LoggerFactory.getLogger(Runner.class);
  private final Command echoCommand = new Echo();
  private final Command typeCommand = new Type();

  public void start() {
    try (Scanner scanner = new Scanner(System.in)) {
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
    } catch (NoSuchElementException | IllegalStateException e) {
      log.info("InputMismatch / Error {}", e.getMessage());
    }
    return lineToRead;
  }

  private void evaluateCommand(final String command) {
    BuiltInCommand builtInCommand = getBuiltInCommand(command);
    if (builtInCommand == null) {
      System.out.println(command + ": command not found");
    } else {
      builtInCommand.execute(this, builtInCommand.name(), command);
    }
  }

  private BuiltInCommand getBuiltInCommand(String command) {
    int firstSpace = command.stripLeading().indexOf(" ");
    int length = (firstSpace == -1) ? command.length() : firstSpace;

    for (BuiltInCommand builtIn : BuiltInCommand.values()) {
      String name = builtIn.name();
      if (name.length() == length
          && command.regionMatches(true, 0, name, 0, length)) {
        return builtIn;
      }
    }
    return null;
  }

  private enum BuiltInCommand {
    EXIT {
      void execute(Runner runner, String builtIn, String command) {
        runner.stop();
      }
    },
    ECHO {
      void execute(Runner runner, String builtIn, String command) {
        runner.echoCommand.execute(builtIn, command);
      }
    },
    TYPE {
      void execute(Runner runner, String builtIn, String command) {
        runner.typeCommand.execute(builtIn, command);
      }
    };

    abstract void execute(Runner context, String builtIn, String command);
  }
}
