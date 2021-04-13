package homework;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

  private final TreeMap<Customer, String> customersData = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

  // todo: 2. надо реализовать методы этого класса
  // важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

  public void add(Customer customer, String data) {
    customersData.put(customer, data);
  }

  public Map.Entry<Customer, String> getSmallest() {
    Map.Entry<Customer, String> firstEntry = customersData.firstEntry();
    return Map.entry(new Customer(firstEntry.getKey()), firstEntry.getValue());
  }

  public Map.Entry<Customer, String> getNext(Customer customer) {
    Map.Entry<Customer, String> nextEntry = customersData.higherEntry(customer);
    if (nextEntry == null) {
      return null;
    }
    return Map.entry(new Customer(nextEntry.getKey()), nextEntry.getValue());
  }
}
