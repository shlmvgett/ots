package ru.otus.services.sessionmanager;

public interface TransactionManager {

  <T> T doInTransaction(TransactionAction<T> action);
}
