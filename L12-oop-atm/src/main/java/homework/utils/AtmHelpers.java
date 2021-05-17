package homework.utils;

import homework.Atm;
import homework.IAtm;
import homework.models.Account;
import homework.models.Banknote;
import homework.models.BanknotesBox;
import homework.models.DebitCard;
import homework.models.IBankCard;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class AtmHelpers {

  private AtmHelpers() {
  }

  public static IBankCard prepareCard() {
    Account account = new Account();
    return new DebitCard(account);
  }

  // Init ATM with test data
  public static IAtm prepareAtm() {
    Map<Currency, BanknotesBox> banknotes = new HashMap<>(Map.of(
        Currency.getInstance("RUB"), new BanknotesBox(new TreeMap<>(Map.of(
            new Banknote(Currency.getInstance("RUB"), 100), 30,
            new Banknote(Currency.getInstance("RUB"), 1_000), 20,
            new Banknote(Currency.getInstance("RUB"), 10_000), 10
            ))),
        Currency.getInstance("USD"), new BanknotesBox(new TreeMap<>(Map.of(
            new Banknote(Currency.getInstance("USD"), 100), 10,
            new Banknote(Currency.getInstance("USD"), 1_000), 5
        )))
    ));
    return new Atm(banknotes);
  }
}
