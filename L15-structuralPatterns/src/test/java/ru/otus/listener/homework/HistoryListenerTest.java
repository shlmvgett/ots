package ru.otus.listener.homework;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

class HistoryListenerTest {

  @Test
  void ListenerTest() {
    //given
    var historyListener = new HistoryListener();

    var id = 100L;
    var data = "33";
    var field13 = new ObjectForMessage();
    field13.setData(List.of(data));

    var message = new Message.Builder(id)
        .field10("field10")
        .field13(field13)
        .build();

    //when
    historyListener.onUpdated(message);
    message.getField13().setData(new ArrayList<>(List.of())); //меняем исходное сообщение

    //then
    var messageFromHistory = historyListener.findMessageById(id);
    assertThat(messageFromHistory).isPresent();
    assertThat(messageFromHistory.get().getField13().getData()).containsExactly(data);
  }
}