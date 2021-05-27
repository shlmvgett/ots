package homework;

import java.util.Objects;

public class Customer {

  private final long id;
  private String name;
  private long scores;

  public Customer(long id, String name, long scores) {
    this.id = id;
    this.name = name;
    this.scores = scores;
  }

  public Customer(Customer customer) {
    this.id = customer.getId();
    this.name = customer.getName();
    this.scores = customer.getScores();
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getScores() {
    return scores;
  }

  public void setScores(long scores) {
    this.scores = scores;
  }

  @Override
  public String toString() {
    return String.format("Customer{id=%s, name='%s', scores=%s}", id, name, scores);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Customer customer = (Customer) o;
    return id == customer.getId();
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
