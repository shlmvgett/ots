package ru.otus.gc;

import java.util.ArrayList;
import java.util.List;

class Benchmark implements BenchmarkMBean {

  private final int loopCounter;
  private volatile int size = 0;
  private List<List<Long>> cache = new ArrayList<>();

  public Benchmark(int loopCounter) {
    this.loopCounter = loopCounter;
  }

  void run() throws InterruptedException {
    for (int idx = 0; idx < loopCounter; idx++) {
      int local = size;
      List<Long> ml = new ArrayList<>();
      for (int i = 0; i < local; i++) {
        ml.add(System.nanoTime());
      }
      cache.add(ml.subList(0, local / 20));
      Thread.sleep(70);
    }
  }

  @Override
  public int getSize() {
    return size;
  }

  @Override
  public void setSize(int size) {
    System.out.println("new size:" + size);
    this.size = size;
  }
}
