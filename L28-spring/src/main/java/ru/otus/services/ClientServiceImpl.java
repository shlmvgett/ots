package ru.otus.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.stereotype.Service;
import ru.otus.domain.Client;

@Service
public class ClientServiceImpl implements ClientService {

  private final DBServiceClient dbServiceClient;

  public ClientServiceImpl(DBServiceClient dbServiceClient) {
    this.dbServiceClient = dbServiceClient;
  }

  @Override
  public List<Client> findAll() {
    return dbServiceClient.findAll();
  }

  @Override
  public Client findById(long id) {
    Optional<Client> client = dbServiceClient.getClient(id);
    return client.orElse(null);
  }

  @Override
  public Client findByName(String name) {
    Optional<Client> client = dbServiceClient.findByName(name);
    return client.orElse(null);
  }

  @Override
  public Client findRandom() {
    List<Client> clients = dbServiceClient.findAll();
    return clients.get(new Random().nextInt(clients.size()));
  }

  @Override
  public Client save(Client client) {
    return dbServiceClient.saveClient(client);
  }
}
