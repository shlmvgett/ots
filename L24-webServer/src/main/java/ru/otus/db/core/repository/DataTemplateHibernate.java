package ru.otus.db.core.repository;

import java.util.List;
import java.util.Optional;
import org.hibernate.Session;

public class DataTemplateHibernate<T> implements DataTemplate<T> {

  private final Class<T> clazz;

  public DataTemplateHibernate(Class<T> clazz) {
    this.clazz = clazz;
  }

  @Override
  public Optional<T> findById(Session session, long id) {
    return Optional.ofNullable(session.find(clazz, id));
  }

  @Override
  public Optional<T> getUserByLogin(Session session, String login) {
    return session.createQuery(String.format("from %s where login=:login", clazz.getSimpleName()), clazz)
        .setParameter("login", login).uniqueResultOptional();
  }

  @Override
  public List<T> getAll(Session session) {
    return session.createQuery(String.format("from %s", clazz.getSimpleName()), clazz).getResultList();
  }

  @Override
  public void insert(Session session, T object) {
    session.persist(object);
  }

  @Override
  public void update(Session session, T object) {
    session.merge(object);
  }
}
