package ru.otus.services;

import java.util.List;
import java.util.Optional;

import ru.otus.domain.Client;

public interface ClientService {

    List<Client> findAll();

//  Client findByName(String name);
//
//  Client findRandom();

    Client save(Client client);
}
