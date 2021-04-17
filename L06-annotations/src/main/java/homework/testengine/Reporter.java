package homework.testengine;

import homework.testengine.models.TestEntity;
import homework.testengine.models.TestStatus;
import java.util.List;
import java.util.stream.Collectors;

public class Reporter {

  private static final String HEADER_TEMPLATE =
      "\nTEST RESULTS:\n\tTOTAL: %s\n\t\tPASSED: %s\n\t\tFAILED: %s\n";
  private final List<TestEntity> tests;

  public Reporter(List<TestEntity> tests) {
    this.tests = tests;
  }

  public void print() {
    List<TestEntity> failedTests =
        tests.stream()
            .filter(t -> t.getResult().getTestStatus().equals(TestStatus.FAILED))
            .collect(Collectors.toList());
    System.out.printf(
        HEADER_TEMPLATE, tests.size(), tests.size() - failedTests.size(), failedTests.size());
  }
}
