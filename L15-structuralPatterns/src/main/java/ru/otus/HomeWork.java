package ru.otus;

import java.util.ArrayList;
import java.util.List;
import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.ListenerPrinterConsole;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.LoggerProcessor;
import ru.otus.processor.ProcessorUpperField10;
import ru.otus.processor.homework.ProcessorSwapField11Field12;
import ru.otus.processor.homework.ProcessorTimeException;

public class HomeWork {

    /*
     Реализовать to do:
       1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
       2. Сделать процессор, который поменяет местами значения field11 и field12
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
            Секунда должна определяьться во время выполнения.
       4. Сделать Listener для ведения истории: старое сообщение - новое (подумайте, как сделать, чтобы сообщения не портились)
     */

  public static void main(String[] args) {
    var processors = List.of(
        new ProcessorTimeException(),
        new ProcessorSwapField11Field12(),
        new LoggerProcessor(
            new ProcessorUpperField10()
        ));
    var complexProcessor = new ComplexProcessor(processors, ex -> {
    });

    var listenerPrinter = new ListenerPrinterConsole();
    var historyListener = new HistoryListener();

    complexProcessor.addListener(listenerPrinter);
    complexProcessor.addListener(historyListener);

    var message = new Message.Builder(1L)
        .field10("field10")
        .field11("field11")
        .field12("field12")
        .field13(new ObjectForMessage(new ArrayList<>(List.of("field13"))))
        .build();

    var result = complexProcessor.handle(message);
    System.out.println("\nResult: " + result);

    complexProcessor.removeListener(listenerPrinter);
  }
}
