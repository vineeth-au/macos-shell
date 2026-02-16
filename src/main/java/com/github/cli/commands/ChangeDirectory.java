package com.github.cli.commands;

import static com.github.cli.utils.ConsoleUtils.CURRENT_USER_DIRECTORY;

import java.io.IOError;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChangeDirectory implements Command {

  Logger log = LoggerFactory.getLogger(ChangeDirectory.class);

  @Override
  public void execute(String builtIn, String argument) {
    String filePath = getArgumentFrom(argument);
    if (isValidFilePath(filePath)) {
      switchDirectory(filePath);
    } else {
      System.out.println("cd: " + filePath + ": No such file or directory");
    }
  }

  private void switchDirectory(String argument) {
    System.setProperty(CURRENT_USER_DIRECTORY, Paths.get(argument).toAbsolutePath().toString());
  }

  private boolean isValidFilePath(final String filePath) {
    try {
      Path currentPath = Paths.get(filePath).toAbsolutePath();
      return Files.isDirectory(currentPath);
    } catch (IOError ioException) {
      log.info("Error while trying access the path {}", filePath);
      return false;
    }
  }
}
