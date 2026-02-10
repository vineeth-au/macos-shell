import com.github.cli.Runner;

void main() {
  Runner cliRunner = new Runner();
  Runtime.getRuntime().addShutdownHook(new Thread(cliRunner::stop));

  cliRunner.start();
}
