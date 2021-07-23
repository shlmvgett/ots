package ru.otus.processor.homework;

import java.time.Clock;
import java.time.LocalTime;
import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorTimeException implements Processor {

  private final Clock clock;

  public ProcessorTimeException() {
    this(Clock.systemDefaultZone());
  }

  public ProcessorTimeException(Clock clock) {
    this.clock = clock;
  }

  @Override
  public Message process(Message message) {
    int second = LocalTime.now(clock).getSecond();
    if (second % 2 == 0) {
      System.out.println("Time exception for second: " + second);
      throw new RuntimeException("Time exception for second: " + second);
    }
    return new Message.Builder(message).build();
  }
}
