import com.github.shell.io.Commands;
import java.util.Scanner;

public class Main {

  void main() {
    Scanner scanner = new Scanner(System.in);
    Commands commands = new Commands(scanner);

    Runtime.getRuntime().addShutdownHook(new Thread(commands::stop));

    commands.startInputController();
  }
}

