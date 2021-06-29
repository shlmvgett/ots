package ru.otus.hibernate.crm.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "phone")
public class Phone {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Column(name = "number")
  private String number;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Client client;

  public Phone() {
  }

  public Phone(String number, Client client) {
    this.number = number;
    this.client = client;
  }

  @Override
  public String toString() {
    return "PhoneDataSet{" +
        "id=" + id +
        ", number='" + number + '\'' +
        '}';
  }
}
