package homework.models;

import java.util.Currency;
import java.util.UUID;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Account {

  private final UUID accountId;
  private final Currency currency;
  private Long amount;

  public Account() {
    this.accountId = UUID.randomUUID();
    this.currency = Currency.getInstance("RUB");
    this.amount = 15_000L;
  }

  public void getMoney(Long requiredAmount) {
    if (requiredAmount > amount) {
      throw new RuntimeException("Not enough money.");
    }
    amount = amount - requiredAmount;
  }

  public boolean hasAmount(Long requiredAmount) {
    return requiredAmount <= amount;
  }

  public void putMoney(Long amount, Currency currency) {
    if (!this.currency.equals(currency)) {
      throw new RuntimeException("Your account doesn't support currency: " + currency.toString());
    }
    this.amount += amount;
  }
}
