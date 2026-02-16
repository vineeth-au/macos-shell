package com.github.cli.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Executable implements Command {

  private final List<String> environmentPaths;
  private final Logger log = LoggerFactory.getLogger(Executable.class);

  public Executable() {
    environmentPaths = List.copyOf(getSystemEnvironmentPaths());
  }

  @Override
  public void execute(String builtIn, String argument) {
    locateExecutableFile(argument);
  }

  private void locateExecutableFile(String argument) {
    String validShellCommand = "";
    for (String eachFilePath : environmentPaths) {
      validShellCommand = checkEachFilePath(eachFilePath, getBuiltInFrom(argument));
      if (!validShellCommand.isEmpty()) {
        try {
          executeShellCommand(argument);
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

  private void executeShellCommand(final String validShellCommand) throws IOException {
    //TODO: Redo this using Process builder
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
}
