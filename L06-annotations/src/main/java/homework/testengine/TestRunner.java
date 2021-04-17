package homework.testengine;

import homework.testengine.models.TestEntity;
import java.util.List;

public class TestRunner {

  private List<TestEntity> tests;

  public TestRunner(Class testClass) {
    this.tests = TestParser.getTests(testClass);
  }

  public void run() {
    tests.forEach(TestEntity::run);
    new Reporter(tests).print();
  }
}
