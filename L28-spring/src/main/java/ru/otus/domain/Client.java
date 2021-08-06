package ru.otus.domain;

import javax.annotation.Nonnull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

@ToString
@Getter
@Setter
@Table("users")
public class Client {

  @Id
  private Long id;

  @Nonnull
  private String name;

  @Nonnull
  private String login;

  @Nonnull
  private String role;

  public Client() {
  }

  public Client(String name, String login, String role) {
    this.id = null;
    this.name = name;
    this.login = login;
    this.role = role;
  }

  @PersistenceConstructor
  public Client(Long id, String name, String login, String role) {
    this.id = id;
    this.name = name;
    this.login = login;
    this.role = role;
  }
}
