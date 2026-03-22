package com.github.cli.command.commands;

import static com.github.cli.command.CommandUtils.checkEachFilePath;
import static com.github.cli.command.CommandUtils.getArgumentFrom;
import static com.github.cli.command.CommandUtils.getBuiltInFrom;
import static com.github.cli.command.CommandUtils.getSystemEnvironmentPaths;
import static com.github.cli.utils.ConsoleUtils.DOUBLE;
import static com.github.cli.utils.ConsoleUtils.ESCAPE;
import static com.github.cli.utils.ConsoleUtils.SINGLE_QUOTE;
import static com.github.cli.utils.ConsoleUtils.SINGLE;

import com.github.cli.command.Command;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Executable implements Command {

  private final List<String> environmentPaths;
  private static final Logger log = LoggerFactory.getLogger(Executable.class);

  public Executable() {
    environmentPaths = List.copyOf(getSystemEnvironmentPaths());
  }

  @Override
  public void execute(String command) {
    locateExecutableFile(command);
  }

  private void locateExecutableFile(String argument) {
    String validShellCommand = "";
    for (String eachFilePath : environmentPaths) {
      validShellCommand = checkEachFilePath(eachFilePath, getBuiltInFrom(argument));
      if (!validShellCommand.isEmpty()) {
        try {
          // TODO: This is a workaround. Ideally would need to check if the builtIn is Native.
          if (!argument.contains(SINGLE_QUOTE)) {
            runExecutables(argument);
          } else {
            runNativeShellCommands(argument);
          }
          break;
        } catch (IOException e) {
          log.error("Exception while trying to run Process {}", validShellCommand, e.getCause());
        }
      }
    }
    if (validShellCommand.isEmpty()) {
      System.out.println(argument + ": not found");
    }
  }

  private void runExecutables(String validShellCommand) throws IOException {
    Process process = Runtime.getRuntime().exec(validShellCommand);
    String eachLine = "";
    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(process.getInputStream()))) {
      while ((eachLine = reader.readLine()) != null) {
        System.out.println(eachLine);
      }
    } catch (IOException e) {
      log.error("Exception while trying to execute Command {}", validShellCommand, e.getCause());
    }
  }

  private void runNativeShellCommands(String command) throws IOException {
    List<ProcessBuilder> processPipeline = getProcessPipeline(command);
    List<Process> processBuilder = ProcessBuilder.startPipeline(processPipeline);

    for (Process process : processBuilder) {
      String eachLine = "";
      try (BufferedReader reader = new BufferedReader(
          new InputStreamReader(process.getInputStream()))) {
        while ((eachLine = reader.readLine()) != null) {
          System.out.print(eachLine);
        }
      } catch (IOException e) {
        log.error("Exception while trying to execute Command {}", command, e.getCause());
      }
    }
    System.out.println();
  }

  private List<ProcessBuilder> getProcessPipeline(String command) {
    String builtInCommand = getBuiltInFrom(command);
    String argument = getArgumentFrom(command);

    List<String> arguments = new ArrayList<>();
    arguments.add(builtInCommand);
    StringBuilder current = new StringBuilder();
    boolean insideQuote = false;
    char openingQuote = SINGLE;

    for (char eachChar : argument.toCharArray()) {
      if (openingQuote == eachChar && eachChar == SINGLE || eachChar == DOUBLE) {
        if (insideQuote) {
          arguments.add(current.toString());
          current.setLength(0);
        }
        insideQuote = !insideQuote;
        openingQuote = eachChar;
      } else if (insideQuote) {
        current.append(eachChar);
      }
    }
    return List.of(new ProcessBuilder(arguments));
  }
}
