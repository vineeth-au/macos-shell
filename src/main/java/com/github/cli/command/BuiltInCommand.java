package com.github.cli.command;

import com.github.cli.Runner;

public enum BuiltInCommand {
  EXIT {
    public void execute(Runner runner, String command) {
      runner.stop();
    }
  },
  ECHO {
    public void execute(Runner runner, String command) {
      runner.echoCommand.execute(command);
    }
  },
  TYPE {
    public void execute(Runner runner, String command) {
      runner.typeCommand.execute(command);
    }
  },
  EXECUTABLE {
    public void execute(Runner runner, String command) {
      runner.shellExecutable.execute(command);
    }
  },
  PWD {
    public void execute(Runner runner, String command) {
      runner.pwdCommand.execute(command);
    }
  },
  CD {
    public void execute(Runner runner, String command) {
      runner.cdCommand.execute(command);
    }
  };

  public abstract void execute(Runner context, String command);
}
