package ru.otus.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.domain.Client;
import ru.otus.repostory.ClientRepository;
import ru.otus.services.sessionmanager.TransactionManager;

@Service
public class DbServiceClientImpl implements DBServiceClient {

  private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

  private final TransactionManager transactionManager;
  private final ClientRepository clientRepository;

  public DbServiceClientImpl(TransactionManager transactionManager, ClientRepository clientRepository) {
    this.transactionManager = transactionManager;
    this.clientRepository = clientRepository;
  }

  @Override
  public Client saveClient(Client client) {
    return transactionManager.doInTransaction(() -> {
      var savedClient = clientRepository.save(client);
      log.info("saved client: {}", savedClient);
      return savedClient;
    });
  }

  @Override
  public Optional<Client> getClient(long id) {
    var clientOptional = clientRepository.findById(id);
    log.info("client: {}", clientOptional);
    return clientOptional;
  }

  @Override
  public Optional<Client> findByName(String name) {
    return clientRepository.findByName(name);
  }

  @Override
  public List<Client> findAll() {
    var clientList = new ArrayList<>(clientRepository.findAll());
    log.info("clientList:{}", clientList);
    return clientList;
  }
}
