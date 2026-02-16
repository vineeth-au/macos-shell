package com.github.cli;

import com.github.cli.commands.Command;
import com.github.cli.commands.Echo;
import com.github.cli.commands.Executable;
import com.github.cli.commands.PrintWorkingDirectory;
import com.github.cli.commands.Type;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Runner {

  private volatile boolean isRunning = true;
  private final Logger log = LoggerFactory.getLogger(Runner.class);

  private final Command echoCommand = new Echo();
  private final Command typeCommand = new Type();
  private final Command shellExecutable = new Executable();
  private final Command pwdCommand = new PrintWorkingDirectory();

  public void start() {
    try (Scanner scanner = new Scanner(System.in)) {
      while (isRunning) {
        String commandToExecute = readInput(scanner);
        evaluateCommand(commandToExecute);
      }
    }
  }

  public void stop() {
    isRunning = false;
  }

  private void evaluateCommand(final String command) {
    BuiltInCommand builtInCommand = getBuiltInCommand(command);
    builtInCommand.execute(this, builtInCommand.name(), command);
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

  private BuiltInCommand getBuiltInCommand(String command) {
    int firstSpaceIndex = command.stripLeading().indexOf(" ");
    int builtInCmdLength = (firstSpaceIndex == -1) ? command.length() : firstSpaceIndex;

    for (BuiltInCommand builtIn : BuiltInCommand.values()) {
      String name = builtIn.name();
      if (name.length() == builtInCmdLength
          && command.regionMatches(true, 0, name, 0, builtInCmdLength)) {
        return builtIn;
      }
    }
    return BuiltInCommand.EXECUTABLE;
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
    },
    EXECUTABLE {
      void execute(Runner runner, String builtIn, String command) {
        runner.shellExecutable.execute(builtIn, command);
      }
    },
    PWD {
      void execute(Runner runner, String builtIn, String command) {
        runner.pwdCommand.execute(builtIn, command);
      }
    };

    abstract void execute(Runner context, String builtIn, String command);
  }
}
