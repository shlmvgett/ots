package homework.testengine.models;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import lombok.Getter;

public class TestEntity {

  private final Class testClass;
  private final List<Method> beforeMethods;
  @Getter private final Method testMethod;
  private final List<Method> afterMethods;
  @Getter private Result result;

  public TestEntity(
      Class testClass, List<Method> beforeMethods, Method testMethod, List<Method> afterMethods) {
    this.testClass = testClass;
    this.beforeMethods = beforeMethods;
    this.testMethod = testMethod;
    this.afterMethods = afterMethods;
  }

  public void run() {
    Object instance = initInstance();
    try {
      for (Method method : beforeMethods) {
        method.invoke(instance);
      }
      testMethod.invoke(instance);

      this.result = new Result(TestStatus.PASSED);
    } catch (Exception e) {
      this.result = new Result(TestStatus.FAILED, e);
    }

    for (Method method : afterMethods) {
      try {
        method.invoke(instance);
      } catch (Exception e) {
        this.result = new Result(TestStatus.FAILED, e);
      }
    }
  }

  private Object initInstance() {
    try {
      return testClass.getConstructor().newInstance();
    } catch (InstantiationException
        | IllegalAccessException
        | InvocationTargetException
        | NoSuchMethodException e) {
      throw new RuntimeException();
    }
  }
}
