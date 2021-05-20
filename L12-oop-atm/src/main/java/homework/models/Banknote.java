package homework.models;

import java.util.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class Banknote {

  private final Currency currency;
  private final Denomination value;

  public Banknote(Integer value) {
    this.currency = Currency.getInstance("RUB");
    this.value = Denomination.fromValue(value);
  }

  public Integer getIntValue() {
    return value.getValue();
  }

}
