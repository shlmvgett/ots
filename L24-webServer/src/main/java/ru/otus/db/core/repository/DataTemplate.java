package ru.otus.db.core.repository;

import java.util.List;
import java.util.Optional;
import org.hibernate.Session;

public interface DataTemplate<T> {

  Optional<T> findById(Session session, long id);

  Optional<T> getUserByLogin(Session session, String login);

  List<T> getAll(Session session);

  void insert(Session session, T object);

  void update(Session session, T object);
}
