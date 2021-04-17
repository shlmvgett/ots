package homework.testengine.models;

import lombok.Getter;

@Getter
public class Result {

  private TestStatus testStatus;
  private Exception exception;

  public Result(TestStatus testStatus, Exception exception) {
    this.testStatus = testStatus;
    this.exception = exception;
  }

  public Result(TestStatus testStatus) {
    this.testStatus = testStatus;
  }
}
