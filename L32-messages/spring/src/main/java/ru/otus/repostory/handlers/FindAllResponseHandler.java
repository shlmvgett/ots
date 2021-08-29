package ru.otus.repostory.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.client.CallbackRegistry;
import ru.otus.messagesystem.client.MessageCallback;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageHelper;
import ru.otus.domain.ClientList;

import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
public class FindAllResponseHandler implements RequestHandler<ClientList> {

    private final CallbackRegistry callbackRegistry;

    @Override
    public Optional<Message> handle(Message msg) {
        try {
            MessageCallback<? extends ResultDataType> callback = callbackRegistry.getAndRemove(msg.getCallbackId());
            callback.accept(MessageHelper.getPayload(msg));
        } catch (Exception e) {
            log.error("Error for message: {}", msg);
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
