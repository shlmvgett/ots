package homework;

import homework.testengine.TestRunner;
import homework.tests.DemoTests;

public class App {

  public static void main(String... args) {
    new TestRunner(DemoTests.class).run();
  }
}
