package ru.otus.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.processor.Processor;
import ru.otus.processor.homework.ProcessorTimeException;

class ComplexProcessorTest {

  @Test
  @DisplayName("ProcessorTimeException генерирует исключение для четной секунды")
  void timeExceptionProcessorWithExceptionTest() {
    Processor processorTimeException =
        new ProcessorTimeException(Clock.fixed(Instant.parse("2021-01-11T10:00:10Z"), ZoneOffset.UTC));
    var message = new Message.Builder(1L).build();

    assertThrows(RuntimeException.class, () -> processorTimeException.process(message));
  }

  @Test
  @DisplayName("ProcessorTimeException не генерирует исключение для нечетной секунды и возвращает message")
  void timeExceptionProcessorWithoutExceptionTest() {
    Processor processorTimeException =
        new ProcessorTimeException(Clock.fixed(Instant.parse("2021-01-11T10:00:11Z"), ZoneOffset.UTC));
    var message = new Message.Builder(1L).build();

    assertThat(processorTimeException.process(message)).isEqualTo(message);
  }

  @Test
  @DisplayName("Тестируем вызовы процессоров")
  void handleProcessorsTest() {
    //given
    var message = new Message.Builder(1L).field7("field7").build();

    var processor1 = mock(Processor.class);
    when(processor1.process(message)).thenReturn(message);

    var processor2 = mock(Processor.class);
    when(processor2.process(message)).thenReturn(message);

    var processors = List.of(processor1, processor2);

    var complexProcessor = new ComplexProcessor(processors, (ex) -> {
    });

    //when
    var result = complexProcessor.handle(message);

    //then
    verify(processor1).process(message);
    verify(processor2).process(message);
    assertThat(result).isEqualTo(message);
  }

  @Test
  @DisplayName("Тестируем обработку исключения")
  void handleExceptionTest() {
    //given
    var message = new Message.Builder(1L).field8("field8").build();

    var processor1 = mock(Processor.class);
    when(processor1.process(message)).thenThrow(new RuntimeException("Test Exception"));

    var processor2 = mock(Processor.class);
    when(processor2.process(message)).thenReturn(message);

    var processors = List.of(processor1, processor2);

    var complexProcessor = new ComplexProcessor(processors, (ex) -> {
      throw new TestException(ex.getMessage());
    });

    //when
    assertThatExceptionOfType(TestException.class).isThrownBy(() -> complexProcessor.handle(message));

    //then
    verify(processor1, times(1)).process(message);
    verify(processor2, never()).process(message);
  }

  @Test
  @DisplayName("Тестируем уведомления")
  void notifyTest() {
    //given
    var message = new Message.Builder(1L).field9("field9").build();

    var listener = mock(Listener.class);

    var complexProcessor = new ComplexProcessor(new ArrayList<>(), (ex) -> {
    });

    complexProcessor.addListener(listener);

    //when
    complexProcessor.handle(message);
    complexProcessor.removeListener(listener);
    complexProcessor.handle(message);

    //then
    verify(listener, times(1)).onUpdated(message);
  }

  private static class TestException extends RuntimeException {

    public TestException(String message) {
      super(message);
    }
  }
}