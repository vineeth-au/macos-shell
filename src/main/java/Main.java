import com.github.shell.io.Commands;

public class Main {

  void main() {
    Commands commands = new Commands();
    Runtime.getRuntime().addShutdownHook(new Thread(commands::stop));

    commands.startInputController();
  }
}
