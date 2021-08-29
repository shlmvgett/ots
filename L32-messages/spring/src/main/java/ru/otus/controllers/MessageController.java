package ru.otus.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.HtmlUtils;
import ru.otus.config.ClientType;
import ru.otus.domain.Client;
import ru.otus.domain.Message;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.message.MessageType;
import ru.otus.domain.ClientList;

@Log4j2
@Controller
public class MessageController {

    private final MsClient msClient;
    private final SimpMessagingTemplate messageTemplate;

    public MessageController(@Qualifier("appMsClient") MsClient msClient, SimpMessagingTemplate messageTemplate) {
        this.msClient = msClient;
        this.messageTemplate = messageTemplate;
    }

    @MessageMapping("/message/client/save")
    public void save(@RequestBody Client client) {
        ru.otus.messagesystem.message.Message outMsg =
                msClient.produceMessage(
                        ClientType.DB_CLIENT.getName(), client, MessageType.USER_DATA, c -> {
                        });
        msClient.sendMessage(outMsg);
    }

    @MessageMapping("/message/client/getAll")
    public void getAll() {
        ru.otus.messagesystem.message.Message outMsg =
                msClient.produceMessage(
                        ClientType.DB_CLIENT.getName(), new ClientList(), MessageType.USER_DATA_LIST, this::sendAll);
        msClient.sendMessage(outMsg);
    }

    private void sendAll(ClientList clientList) {
        messageTemplate.convertAndSend("/topic/clients/all", clientList.getClients());
    }

    @MessageMapping("/message.{roomId}")
    @SendTo("/topic/response.{roomId}")
    public Message getMessage(@DestinationVariable String roomId, Message message) {
        log.info("got message:{}, roomId:{}", message, roomId);
        return new Message(HtmlUtils.htmlEscape(message.getMessage()));
    }
}
