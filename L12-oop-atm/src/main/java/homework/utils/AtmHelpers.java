package homework.utils;

import homework.Atm;
import homework.AtmImpl;
import homework.models.Account;
import homework.models.Banknote;
import homework.models.BanknotesBox;
import homework.models.BanknotesBoxImpl;
import homework.models.DebitCard;
import homework.models.Denomination;
import homework.models.BankCard;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AtmHelpers {

  private AtmHelpers() {
  }

  public static BankCard prepareCard() {
    Account account = new Account();
    return new DebitCard(account);
  }

  // Init ATM with test data
  public static Atm prepareAtm() {
    Map<Currency, BanknotesBox> banknotes = new HashMap<>(Map.of(
        Currency.getInstance("RUB"), new BanknotesBoxImpl(
            new TreeMap<>(Map.of(
                Denomination.D_100, List.of(new Banknote(100), new Banknote(100), new Banknote(100), new Banknote(100), new Banknote(100), new Banknote(100)),
                Denomination.D_1000,
                List.of(new Banknote(1000), new Banknote(1000), new Banknote(1000), new Banknote(1000), new Banknote(1000), new Banknote(1000),
                    new Banknote(1000), new Banknote(1000)),
                Denomination.D_10000,
                List.of(new Banknote(10_000), new Banknote(10_000), new Banknote(10_000), new Banknote(10_000), new Banknote(10_000), new Banknote(10_000),
                    new Banknote(10_000))
            )))
    ));
    return new AtmImpl(banknotes);
  }
}
