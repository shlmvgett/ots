package ru.otus.repostory.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ru.otus.domain.Client;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import ru.otus.messagesystem.message.MessageHelper;
import ru.otus.services.ClientService;

import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
public class SaveRequestHandler implements RequestHandler<Client> {

    private final ClientService clientService;

    @Override
    public Optional<Message> handle(Message msg) {
        Client client = clientService.save(MessageHelper.getPayload(msg));
        return Optional.of(MessageBuilder.buildReplyMessage(msg, client));
    }
}
