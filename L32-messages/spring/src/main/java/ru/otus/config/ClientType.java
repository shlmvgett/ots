package ru.otus.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClientType {

    DB_CLIENT("dbClient"),
    APP_CLIENT("appClient");

    private final String name;
}
