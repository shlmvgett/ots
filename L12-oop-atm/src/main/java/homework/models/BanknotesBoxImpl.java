package homework.models;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.Getter;

public class BanknotesBoxImpl implements BanknotesBox {

  @Getter
  private final Map<Currency, Map<Denomination, List<Banknote>>> availableBanknotes = new HashMap<>();

  public BanknotesBoxImpl(Currency currency, Map<Denomination, List<Banknote>> availableBanknotes) {
    this.availableBanknotes.put(currency, availableBanknotes);
  }

  public void putMoney(Banknote banknote) {
    Denomination denomination = banknote.getValue();
    if (availableBanknotes.containsKey(banknote.getCurrency())) {

      if (availableBanknotes.get(banknote.getCurrency()).containsKey(denomination)) {
        var tempBanknotes = new ArrayList<>(availableBanknotes.get(banknote.getCurrency()).get(denomination));
        tempBanknotes.add(banknote);
        availableBanknotes.get(banknote.getCurrency()).put(denomination, tempBanknotes);
      } else {
        availableBanknotes.get(banknote.getCurrency()).put(denomination, new ArrayList<>(List.of(banknote)));
      }

    } else {
      availableBanknotes.put(banknote.getCurrency(),
          new TreeMap<>(Map.of(banknote.getValue(), new ArrayList<>(List.of(banknote)))));
    }
  }

  public void getMoney(Long amount, Currency currency) {
    var tempBanknotes = new TreeMap<>(availableBanknotes.get(currency));
    int foundedAmount = 0;

    for (Denomination denomination : tempBanknotes.descendingKeySet()) {
      var banknotes = new ArrayList<>(tempBanknotes.get(denomination));

      while (foundedAmount < amount && banknotes.size() > 0) {
        if (foundedAmount + denomination.getValue() <= amount) {
          foundedAmount = foundedAmount + denomination.getValue();
          banknotes.remove(banknotes.size() - 1);
        } else {
          break;
        }
      }
      tempBanknotes.put(denomination, banknotes);
    }

    if (foundedAmount < amount) {
      throw new RuntimeException("Not enough money in the ATM.");
    }
    availableBanknotes.get(currency).putAll(tempBanknotes);
  }
}
