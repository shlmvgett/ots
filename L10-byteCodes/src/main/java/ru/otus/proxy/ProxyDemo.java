package ru.otus.proxy;

public class ProxyDemo {

  public static void main(String[] args) {
    MyClassInterface myClass = Ioc.createMyClass();
    myClass.methodWithLog("param1");    // The first method call: check and cache that method has Logger
    myClass.methodWithLog("param1");    // The second method call: get Logger status from cache
    myClass.methodWithLog("param1", "param2"); // Call overwritten method w/o Logger
    myClass.methodWithoutLog("param2"); // Call method w/o Logger
  }
}