package ru.otus.services;

import java.util.Optional;
import ru.otus.db.core.repository.DataTemplateHibernate;
import ru.otus.db.core.sessionmanager.TransactionManager;
import ru.otus.db.crm.model.User;
import ru.otus.db.crm.service.DbServiceClientImpl;

public class UserAuthServiceImpl implements UserAuthService {

  private final TransactionManager transactionManager;

  public UserAuthServiceImpl(TransactionManager transactionManager) {
    this.transactionManager = transactionManager;
  }

  @Override
  public boolean authenticate(String login, String password) {
    var dbServiceClient = new DbServiceClientImpl(transactionManager, new DataTemplateHibernate<>(User.class));
    Optional<User> user = dbServiceClient.getUserByLogin(login);
    return user.isPresent()
        && user.get().getPassword().equals(password)
        && user.get().getRole().equalsIgnoreCase("admin");
  }
}
