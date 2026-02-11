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
import java.util.Optional;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Type implements Command {

  private final Logger log = LoggerFactory.getLogger(Type.class);

  @Override
  public void execute(String builtIn, String command) {
    String argument = getArgumentFromCommand(command);
    boolean isBuiltIn = isBuiltIn(argument);

    if (!isBuiltIn) {
      List<String> pathList = getPaths();
      String validCommand = "";
      for (String eachPath : pathList) {
         validCommand = checkEachFilePath(eachPath, argument);
         if (!validCommand.isEmpty()) {
           break;
         }
      }
      if (validCommand.isEmpty()) {
        System.out.println(argument+": not found");
      } else {
        System.out.println(argument+" is "+validCommand);
      }
    }
  }

  private String checkEachFilePath(String filePath, String argument) {
    Optional<String> command;
    try {
      File file = new File(filePath);
      if (file.exists()) {
        List<String> executableFiles = getExecutableFilesFrom(filePath);
        if (!executableFiles.isEmpty()) {
          return executableFiles.stream()
              .filter(f -> f.contains(argument))
              .findFirst()
              .orElse("");
        }
      }
    } catch (IOException e) {
      System.out.println("File Operation Error " + e.getMessage());
    }
    return "";
  }

  private List<String> getExecutableFilesFrom(String filePath) throws IOException{
    try (Stream<Path> fileStream = Files.list(Paths.get(filePath))) {

      List<String> list = fileStream.filter(f -> !Files.isDirectory(f))
          .map(Path::getFileName)
          .filter(Files::isExecutable)
          .map(Path::toString)
          .toList();
      return list;
    }
  }

  private List<String> getPaths() {
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
      throw new IllegalStateException("PATH is Empty!!!" + pathParameters);
    }
    return pathList;
  }

  private boolean isBuiltIn(String argument) {
//    String isBuiltIn = getArgumentFromCommand(argument);
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
