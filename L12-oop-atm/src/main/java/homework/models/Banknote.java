package homework.models;

import java.util.Currency;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class Banknote implements Comparable<Banknote> {

  private final Currency currency;
  private final Integer value;

  public Banknote(Integer value) {
    this.currency = Currency.getInstance("RUB");
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Banknote banknote = (Banknote) o;
    return value.equals(banknote.getValue());
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public int compareTo(Banknote banknote) {
    return Integer.compare(value, banknote.getValue());
  }
}
