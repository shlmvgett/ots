package homework.models;

import java.util.Currency;
import java.util.UUID;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DebitCard implements IBankCard {

  private final UUID cardNumber;
  private final String cardHolder;
  private final Account account;

  public DebitCard(Account account) {
    this.cardNumber = UUID.randomUUID();
    this.cardHolder = "John Doe";
    this.account = account;
  }

  public void putMoney(Long amount, Currency currency) {
    account.putMoney(amount, currency);
  }

  public void getMoney(Long amount) {
    account.getMoney(amount);
  }

  public String getReadableAmount() {
    return account.getAmount() + " " + account.getCurrency();
  }
}
