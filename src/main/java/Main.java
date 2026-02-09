import com.github.shell.io.Commands;

void main() {
  Scanner scanner = new Scanner(System.in);
  Commands commands = new Commands(scanner);

  commands.startInputController();
}

