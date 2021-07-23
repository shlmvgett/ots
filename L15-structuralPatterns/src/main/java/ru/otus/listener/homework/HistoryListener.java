package ru.otus.listener.homework;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ru.otus.listener.Listener;
import ru.otus.model.Message;

public class HistoryListener implements Listener, HistoryReader {

  private final List<Message> history = new ArrayList<>();

  @Override
  public void onUpdated(Message msg) {
    history.add((Message) msg.clone());
    System.out.println("HistoryListener: messages in history: " + history.size());
  }

  @Override
  public Optional<Message> findMessageById(long id) {
    return history.stream().findFirst().filter(m -> m.getId() == id);
  }
}
