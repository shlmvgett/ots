package ru.otus.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import ru.otus.domain.Client;
import ru.otus.repostory.ClientRepository;
import ru.otus.services.sessionmanager.TransactionManager;

@Service
public class ClientServiceImpl implements ClientService {

    private final TransactionManager transactionManager;
    private final ClientRepository clientRepository;

    public ClientServiceImpl(TransactionManager transactionManager, ClientRepository clientRepository) {
        this.transactionManager = transactionManager;
        this.clientRepository = clientRepository;
    }

    @Override
    public Client save(Client client) {
        return transactionManager.doInTransaction(() -> clientRepository.save(client));
    }

    @Override
    public List<Client> findAll() {
        var clientList = new ArrayList<Client>();
        clientRepository.findAll().forEach(clientList::add);
        return clientList;
    }
}
