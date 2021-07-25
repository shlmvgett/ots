package ru.otus.db.crm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements Cloneable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  private Long id;

  @Column(name = "login")
  private String login;

  @Column(name = "name")
  private String name;

  @Column(name = "password")
  private String password;

  @Column(name = "role")
  private String role;

  public User() {
  }

  public User(Long id, String login, String name, String password, String role) {
    this.id = id;
    this.login = login;
    this.name = name;
    this.password = password;
    this.role = role;
  }

  @Override
  public User clone() {
    return new User(this.id, this.login, this.name, this.password, this.role);
  }
}
