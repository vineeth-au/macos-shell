package com.github.cli.commands;

import static com.github.cli.utils.ConsoleUtils.PARENT_DIRECTORY;
import static com.github.cli.utils.ConsoleUtils.USER_DIRECTORY;
import static com.github.cli.utils.ConsoleUtils.DOT;
import static com.github.cli.utils.ConsoleUtils.FORWARD_SLASH;
import static com.github.cli.utils.ConsoleUtils.HOME;
import static com.github.cli.utils.ConsoleUtils.TILDE;

import java.io.IOError;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChangeDirectory implements Command {

  @Getter
  @Setter
  private String pathToUpdate;
  private static final Logger log = LoggerFactory.getLogger(ChangeDirectory.class);

  @Override
  public void execute(String builtIn, String argument) {
    String filePath = getArgumentFrom(argument);
    if (!isValidFilePath(filePath)) {
      System.out.println("cd: " + filePath + ": No such file or directory");
      return;
    }
    switchDirectory();
  }

  // TODO: Check if System.setProperty is the best way to go?
  private void switchDirectory() {
    System.setProperty(USER_DIRECTORY, Paths.get(getPathToUpdate()).toAbsolutePath().toString());
  }

  private boolean isValidFilePath(final String filePath) {
    return filePath.startsWith(FORWARD_SLASH) ? isAbsolutePath(filePath) : isRelativePath(filePath);
  }

  private boolean isRelativePath(String filePath) {
    try {
      if (filePath.startsWith(TILDE)) {
        return updateHomeDirectoryPath();
      } else if (filePath.startsWith(PARENT_DIRECTORY)) {
        return updateParentDirectoryPath(filePath);
      } else {
        return updateCurrentDirectoryPath(filePath);
      }
    } catch (IOError ioError) {
      log.info("Error while trying access the relative path {}", filePath,
          ioError.fillInStackTrace());
    }
    return false;
  }

  private boolean isAbsolutePath(String filePath) throws IOError {
    Path currentPath = Paths.get(filePath).toAbsolutePath();
    return isValidDirectory(currentPath);
  }

  private boolean updateHomeDirectoryPath() throws IOError {
    String homeDirectory = System.getenv(HOME);
    Path currentPath = Paths.get(homeDirectory).toAbsolutePath();
    return isValidDirectory(currentPath);
  }

  private boolean updateCurrentDirectoryPath(String filePath) throws IOError {
    String currentDirectory = System.getProperty(USER_DIRECTORY);
    if (filePath.startsWith("./")) {
      filePath = filePath.substring(2);
    }
    Path currentPath = Paths.get(currentDirectory).resolve(filePath).toAbsolutePath();
    return isValidDirectory(currentPath);
  }

  private boolean updateParentDirectoryPath(String filePath) throws IOError {
    String currentDirectory = System.getProperty(USER_DIRECTORY);
    Path currentPath = Paths.get(currentDirectory).resolve(filePath).normalize();
    return isValidDirectory(currentPath);
  }

  private boolean isValidDirectory(Path currentPath) {
    if (!Files.isDirectory(currentPath)) {
      return false;
    }
    setPathToUpdate(currentPath.toString());
    return true;
  }
}
