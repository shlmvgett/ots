package ru.otus.services;

import java.util.List;
import java.util.Optional;
import ru.otus.domain.Client;

public interface DBServiceClient {

  Client saveClient(Client client);

  Optional<Client> getClient(long id);

  Optional<Client> findByName(String name);

  List<Client> findAll();
}
