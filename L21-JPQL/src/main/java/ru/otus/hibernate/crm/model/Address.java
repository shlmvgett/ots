package ru.otus.hibernate.crm.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Column(name = "street")
  private String street;

  @OneToOne(mappedBy = "address", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Client client;

  public Address() {
  }

  public Address(String street) {
    this.street = street;
  }

  @Override
  public String toString() {
    return "AddressDataSet{" +
        "id=" + id +
        ", street='" + street + '\'' +
        '}';
  }
}
