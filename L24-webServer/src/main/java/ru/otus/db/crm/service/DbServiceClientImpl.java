package ru.otus.db.crm.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.db.core.repository.DataTemplate;
import ru.otus.db.core.sessionmanager.TransactionManager;
import ru.otus.db.crm.model.User;

public class DbServiceClientImpl implements DBServiceClient {

  private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

  private final DataTemplate<User> clientDataTemplate;
  private final TransactionManager transactionManager;

  public DbServiceClientImpl(TransactionManager transactionManager, DataTemplate<User> clientDataTemplate) {
    this.transactionManager = transactionManager;
    this.clientDataTemplate = clientDataTemplate;
  }

  @Override
  public User saveClient(User user) {
    return transactionManager.doInTransaction(session -> {
      if (user.getId() == null) {
        clientDataTemplate.insert(session, user);
        log.info("created client: {}", user);
        return user;
      }
      clientDataTemplate.update(session, user);
      log.info("updated client: {}", user);
      return user;
    });
  }

  @Override
  public Optional<User> getClient(long id) {
    return transactionManager.doInTransaction(session -> {
      var clientOptional = clientDataTemplate.findById(session, id);
      log.info("client: {}", clientOptional);
      return clientOptional;
    });
  }

  @Override
  public Optional<User> getUserByLogin(String login) {
    return transactionManager.doInTransaction(session -> {
      var clientOptional = clientDataTemplate.getUserByLogin(session, login);
      log.info("client: {}", clientOptional);
      return clientOptional;
    });
  }

  @Override
  public List<User> findAll() {
    return transactionManager.doInTransaction(session -> {
      var clientList = clientDataTemplate.getAll(session);
      log.info("clientList:{}", clientList);
      return clientList;
    });
  }
}

