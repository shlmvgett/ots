package ru.otus.db.crm.service;


import java.util.List;
import java.util.Optional;
import ru.otus.db.crm.model.User;

public interface DBServiceClient {

  User saveClient(User user);

  Optional<User> getClient(long id);

  Optional<User> getUserByLogin(String login);

  List<User> findAll();
}
