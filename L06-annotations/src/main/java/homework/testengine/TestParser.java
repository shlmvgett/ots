package homework.testengine;

import homework.annotations.After;
import homework.annotations.Before;
import homework.annotations.Test;
import homework.testengine.models.TestEntity;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestParser {

  private TestParser() {}

  public static List<TestEntity> getTests(Class testClass) {
    Method[] testClassMethods = testClass.getDeclaredMethods();

    List<Method> beforeMethods = getMethods(testClassMethods, Before.class);
    List<Method> afterMethods = getMethods(testClassMethods, After.class);
    List<Method> testMethods = getMethods(testClassMethods, Test.class);
    System.out.println("Found tests: " + testMethods.size());

    List<TestEntity> testEntities = new ArrayList<>();
    for (Method testMethod : testMethods) {
      testEntities.add(new TestEntity(testClass, beforeMethods, testMethod, afterMethods));
    }
    return testEntities;
  }

  private static List<Method> getMethods(Method[] testClassMethods, Class annotationClass) {
    return Arrays.stream(testClassMethods)
        .filter(tm -> tm.isAnnotationPresent(annotationClass))
        .collect(Collectors.toList());
  }
}
