package com.github.cli;

import com.github.cli.commands.ChangeDirectory;
import com.github.cli.commands.Command;
import com.github.cli.commands.Echo;
import com.github.cli.commands.Executable;
import com.github.cli.commands.PrintWorkingDirectory;
import com.github.cli.commands.Type;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Runner {

  private volatile boolean isRunning = true;
  private static final Logger log = LoggerFactory.getLogger(Runner.class);

  private final Command echoCommand = new Echo();
  private final Command typeCommand = new Type();
  private final Command shellExecutable = new Executable();
  private final Command pwdCommand = new PrintWorkingDirectory();
  private final Command cdCommand = new ChangeDirectory();

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

  private String readInput(final Scanner scanner) {
    System.out.print("$ ");
    try {
      return scanner.nextLine();
    } catch (NoSuchElementException | IllegalStateException e) {
      stop();
      log.info("InputMismatch / Error {}", e.getMessage());
      return null;
    }
  }

  private void evaluateCommand(String command) {
    if (command == null || command.isEmpty()) {
      return;
    }
    command = command.strip();
    if (command.isBlank()) {
      return;
    }
    BuiltInCommand builtInCommand = getBuiltInCommand(command);
    try{
      builtInCommand.execute(this, command);
    } catch (Exception e) {
      log.error("Exception while trying to run execute", e.getCause());
    }
  }

  private BuiltInCommand getBuiltInCommand(final String command) {
    int firstSpaceIndex = command.indexOf(" ");
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
      void execute(Runner runner, String command) {
        runner.stop();
      }
    },
    ECHO {
      void execute(Runner runner, String command) {
        runner.echoCommand.execute(command);
      }
    },
    TYPE {
      void execute(Runner runner, String command) {
        runner.typeCommand.execute(command);
      }
    },
    EXECUTABLE {
      void execute(Runner runner, String command) {
        runner.shellExecutable.execute(command);
      }
    },
    PWD {
      void execute(Runner runner, String command) {
        runner.pwdCommand.execute(command);
      }
    },
    CD {
      void execute(Runner runner, String command) {
        runner.cdCommand.execute(command);
      }
    };

    abstract void execute(Runner context, String command);
  }
}
