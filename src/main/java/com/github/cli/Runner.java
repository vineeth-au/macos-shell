package com.github.cli;

import com.github.cli.command.BuiltInCommand;
import com.github.cli.command.commands.ChangeDirectory;
import com.github.cli.command.Command;
import com.github.cli.command.commands.Echo;
import com.github.cli.command.commands.Executable;
import com.github.cli.command.commands.PrintWorkingDirectory;
import com.github.cli.command.commands.Type;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Runner {

  private volatile boolean isRunning = true;
  private static final Logger log = LoggerFactory.getLogger(Runner.class);

  public final Command echoCommand = new Echo();
  public final Command typeCommand = new Type();
  public final Command shellExecutable = new Executable();
  public final Command pwdCommand = new PrintWorkingDirectory();
  public final Command cdCommand = new ChangeDirectory();

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

}
