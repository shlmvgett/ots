package homework.tests;

import static org.assertj.core.api.Assertions.assertThat;

import homework.annotations.After;
import homework.annotations.Before;
import homework.annotations.Test;

public class DemoTests {

  @Before
  public static void beforeTestA() {
    System.out.println("Before test: A");
  }

  @Before
  public static void beforeTestB() {
    System.out.println("Before test: B");
  }

  @After
  public static void afterTestA() {
    System.out.println("After test: A");
  }

  @After
  public static void afterTestB() {
    System.out.println("After test: B\n");
  }

  ////

  @Test
  public void greenATest() {
    System.out.println("Test 1: should be green");
    assertThat(true).isTrue();
  }

  @Test
  public void redTest() {
    System.out.println("Test 2: should be red (by assert)");
    assertThat(true).isFalse();
  }

  @Test
  public void exceptionTest() throws Exception {
    System.out.println("Test 3: should be red (by Exception)");
    throw new Exception();
  }
}
