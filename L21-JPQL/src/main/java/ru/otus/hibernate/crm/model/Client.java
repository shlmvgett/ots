package ru.otus.hibernate.crm.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Table(name = "client")
public class Client implements Cloneable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "address_id")
  private Address address;

  @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Phone> phones;

  public Client() {
  }

  public Client(String name) {
    this.name = name;
  }

  public Client(Long id, String name, Address address, List<Phone> phones) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.phones = phones;
  }

  @Override
  public Client clone() {
    return new Client(this.id, this.name, this.address, this.phones);
  }
}
