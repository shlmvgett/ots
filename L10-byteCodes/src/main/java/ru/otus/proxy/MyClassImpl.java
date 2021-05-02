package ru.otus.proxy;

import ru.otus.proxy.annotations.Log;

public class MyClassImpl implements MyClassInterface {

  @Log
  @Override
  public void methodWithLog(String param) {
    System.out.println("I'm method with Logger, params: " + param + "\n");
  }

  @Override
  public void methodWithLog(String param1, String param2) {
    System.out.println("I'm overwritten method w/o Logger, params: " + param1 + ", " + param2 + "\n");
  }

  @Override
  public void methodWithoutLog(String param) {
    System.out.println("I'm method w/o Logger, params: " + param + "\n");
  }

  @Override
  public String toString() {
    return "MyClassImpl{}";
  }
}
