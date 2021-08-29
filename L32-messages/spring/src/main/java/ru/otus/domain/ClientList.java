package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.otus.domain.Client;
import ru.otus.messagesystem.client.ResultDataType;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientList extends ResultDataType {

    private List<Client> clients;
}
