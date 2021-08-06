package ru.otus;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadsApp {

  private static final Logger log = LoggerFactory.getLogger(ThreadsApp.class);
  private int lastThread = 1;

  public static void main(String[] args) {
    new ThreadsApp().init();
  }

  private void init() {
    Thread thread1 = new Thread(() -> this.go(0));
    Thread thread2 = new Thread(() -> this.go(1));

    thread1.start();
    thread2.start();
  }

  @SneakyThrows
  private synchronized void go(int threadNumber) {
    int iterationsNumber = 20;
    for (int i = 0; i < iterationsNumber; i++) {
      while (lastThread == threadNumber) {
        wait();
      }

      if (i <= iterationsNumber / 2) {
        log.info(Thread.currentThread().getName() + ": " + i);
      } else {
        log.info(Thread.currentThread().getName() + ": " + (iterationsNumber - i));
      }

      Thread.sleep(200);
      lastThread = threadNumber;
      notifyAll();
    }
  }
}
