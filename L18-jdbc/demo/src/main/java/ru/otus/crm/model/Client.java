package ru.otus.crm.model;


import ru.otus.crm.service.annotations.Id;

public class Client {

  @Id
  private Long id;
  private String name;

  public Client() {
    this.id = System.currentTimeMillis() / 1000L;;
    this.name = String.valueOf(id.hashCode());
  }

  public Client(String name) {
//    this.id = System.currentTimeMillis() / 1000L;;
    this.name = name;
  }

  public Client(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Client{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
