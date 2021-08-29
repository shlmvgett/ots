package ru.otus.repostory.handlers;

import lombok.AllArgsConstructor;
import ru.otus.domain.Client;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import ru.otus.domain.ClientList;
import ru.otus.services.ClientService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class FindAllRequestHandler implements RequestHandler<ClientList> {

    private final ClientService clientService;

    @Override
    public Optional<Message> handle(Message msg) {
        List<Client> clients = clientService.findAll();
        return Optional.of(MessageBuilder.buildReplyMessage(msg, new ClientList(clients)));
    }
}
