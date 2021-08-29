package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.otus.messagesystem.HandlersStore;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.MessageSystemImpl;
import ru.otus.messagesystem.client.CallbackRegistry;
import ru.otus.messagesystem.client.CallbackRegistryImpl;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.message.MessageType;
import ru.otus.services.ClientService;
import ru.otus.repostory.handlers.*;

import javax.annotation.PostConstruct;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ClientService clientService;

    public WebConfig(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/no-handler-view").setViewName("noHandlerView");
    }

    @PostConstruct
    public void initMessageSystem() {
        messageSystem().addClient(appMsClient());
        messageSystem().addClient(dbMsClient());
    }

    @Bean
    public MessageSystem messageSystem() {
        return new MessageSystemImpl();
    }

    @Bean
    public CallbackRegistry callbackRegistry() {
        return new CallbackRegistryImpl();
    }

    @Bean
    public MsClient dbMsClient() {
        return new MsClientImpl(ClientType.DB_CLIENT.getName(), messageSystem(), getDbHandlers(), callbackRegistry());
    }

    @Bean
    public MsClient appMsClient() {
        return new MsClientImpl(ClientType.APP_CLIENT.getName(), messageSystem(), getAppHandlers(), callbackRegistry());
    }

    private HandlersStore getDbHandlers() {
        HandlersStore dbHandlersStore = new HandlersStoreImpl();
        dbHandlersStore.addHandler(MessageType.USER_DATA, new SaveRequestHandler(clientService));
        dbHandlersStore.addHandler(MessageType.USER_DATA_LIST, new FindAllRequestHandler(clientService));
        return dbHandlersStore;
    }

    private HandlersStore getAppHandlers() {
        HandlersStore frontendHandlerStore = new HandlersStoreImpl();
        frontendHandlerStore.addHandler(MessageType.USER_DATA, new SaveResponseHandler(callbackRegistry()));
        frontendHandlerStore.addHandler(MessageType.USER_DATA_LIST, new FindAllResponseHandler(callbackRegistry()));
        return frontendHandlerStore;
    }
}
