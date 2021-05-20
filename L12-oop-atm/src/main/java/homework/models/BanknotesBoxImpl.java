package homework.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.Getter;

public class BanknotesBoxImpl implements BanknotesBox {

  @Getter
  private Map<Denomination, List<Banknote>> availableBanknotes = new TreeMap<>(Comparator.comparingInt(Denomination::getValue));


  public BanknotesBoxImpl(Denomination denomination, Banknote banknote) {
    availableBanknotes = new TreeMap<>(Map.of(denomination, new ArrayList<>(List.of(banknote))));
  }

  public BanknotesBoxImpl(Map<Denomination, List<Banknote>> availableBanknotes) {
    this.availableBanknotes.putAll(availableBanknotes);
  }

  public void putMoney(Banknote banknote) {
    Denomination denomination = banknote.getValue();
    if (availableBanknotes.containsKey(denomination)) {
      var tempBanknotes = new ArrayList<>(availableBanknotes.get(denomination));
      tempBanknotes.add(banknote);
      availableBanknotes.put(denomination, tempBanknotes);
    } else {
      availableBanknotes.put(denomination, new ArrayList<>(List.of(banknote)));
    }
  }

  public void getMoney(Long amount) {
    var tempBanknotes = new TreeMap<>(availableBanknotes);
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
    availableBanknotes.putAll(tempBanknotes);
  }
}
