package com.github.cli.commands;

import static com.github.cli.utils.ConsoleUtils.COMMAND_IS;
import static com.github.cli.utils.ConsoleUtils.PATH;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Type implements Command {

  private final Logger log = LoggerFactory.getLogger(Type.class);

  @Override
  public void execute(String builtIn, String command) {
    String argument = getArgumentFromCommand(command);
    boolean isBuiltInCommand = isBuiltIn(argument);
    if (!isBuiltInCommand) {
      locateExecutableFile(argument);
    }
  }

  private void locateExecutableFile(String argument) {
    String validCommand = "";
    for (String eachFilePath : getSystemEnvironmentPaths()) {
      validCommand = checkEachFilePath(eachFilePath, argument);
      if (!validCommand.isEmpty()) {
        System.out.println(argument + " is " + eachFilePath + "/" + argument);
        break;
      }
    }
    if (validCommand.isEmpty()) {
      System.out.println(argument + ": not found");
    }
  }

  private String checkEachFilePath(String filePath, String argument) {
    try {
      File file = new File(filePath);
      if (file.exists()) {
        List<String> executableFiles = getExecutableFilesFrom(filePath);
        if (!executableFiles.isEmpty()) {
          return executableFiles.stream()
              .filter(f -> f.equalsIgnoreCase(argument))
              .findFirst()
              .orElse("");
        }
      }
    } catch (IOException e) {
      log.error("File Operation Error {}", e.getMessage());
    }
    return "";
  }

  private List<String> getExecutableFilesFrom(String filePath) throws IOException {
    try (Stream<Path> fileStream = Files.list(Paths.get(filePath))) {
      return fileStream
          .filter(f -> !Files.isDirectory(f))
          .filter(Files::isExecutable)
          .map(Path::getFileName)
          .map(Path::toString)
          .toList();
    }
  }

  private List<String> getSystemEnvironmentPaths() {
    String pathParameters = System.getenv(PATH);
    List<String> pathList = new ArrayList<>();

    StringBuilder stringBuilder = new StringBuilder();
    for (Character eachChar : pathParameters.toCharArray()) {
      if (eachChar != ':') {
        stringBuilder.append(eachChar);
      } else {
        pathList.add(stringBuilder.toString());
        stringBuilder.delete(0, stringBuilder.length());
      }
    }
    if (pathList.size() < 2) {
      log.error("Path is empty!!! {}", pathParameters);
    }
    return pathList;
  }

  private boolean isBuiltIn(String argument) {
    if (COMMAND_IS(argument)) {
      System.out.println(argument + " is a shell builtin");
      return true;
    }
    return false;
  }

  private String getArgumentFromCommand(String command) {
    return getArgumentFrom(command);
  }
}
