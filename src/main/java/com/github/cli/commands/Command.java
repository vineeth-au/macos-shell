package com.github.cli.commands;

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

public interface Command {
  Logger log = LoggerFactory.getLogger(Command.class);

  void execute(final String builtIn, final String argument);

  default String getBuiltInFrom(final String command) {
    int firstSpaceIndex = command.indexOf(" ");
    if (firstSpaceIndex == -1) {
      return command;
    } else {
      return command.substring(0, firstSpaceIndex);
    }
  }

  default String getArgumentFrom(final String command) {
    int firstSpaceIndex = command.indexOf(" ");
    if (firstSpaceIndex == -1) {
      return command;
    } else {
      return command.substring(firstSpaceIndex + 1);
    }
  }

  default List<String> getSystemEnvironmentPaths() {
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
    if (!stringBuilder.isEmpty()) {
      pathList.add(stringBuilder.toString());
    }
    if (pathList.isEmpty()) {
      log.error("Path is empty!!! {}", pathParameters);
    }
    return pathList;
  }

  default String checkEachFilePath(String filePath, String argument) {
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
}
