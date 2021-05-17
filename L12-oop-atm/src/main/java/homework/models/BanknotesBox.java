package homework.models;

import java.util.Map;
import java.util.TreeMap;

public class BanknotesBox {

  private TreeMap<Banknote, Integer> availableBanknotes = new TreeMap<>();

  public BanknotesBox(Banknote banknote) {
    availableBanknotes = new TreeMap<>(Map.of(banknote, 1));
  }

  public BanknotesBox(Map<Banknote, Integer> availableBanknotes) {
    this.availableBanknotes.putAll(availableBanknotes);
  }

  public void putMoney(Banknote banknote) {
    if (availableBanknotes.containsKey(banknote)) {
      availableBanknotes.put(banknote, availableBanknotes.get(banknote) + 1);
    } else {
      availableBanknotes.put(banknote, 1);
    }
  }

  public void getMoney(Long amount) {
    TreeMap<Banknote, Integer> tempBanknotes = new TreeMap<>(availableBanknotes);
    int foundedAmount = 0;

    for (Banknote banknoteType : tempBanknotes.descendingKeySet()) {
      int banknoteTypeCounter = tempBanknotes.get(banknoteType);

      while (foundedAmount < amount && banknoteTypeCounter > 0) {
        if (foundedAmount + banknoteType.getValue() <= amount) {
          foundedAmount = foundedAmount + banknoteType.getValue();
          banknoteTypeCounter--;
        } else {
          break;
        }
      }
      tempBanknotes.put(banknoteType, banknoteTypeCounter);
    }

    if (foundedAmount < amount) {
      throw new RuntimeException("Not enough money in the ATM.");
    }
    availableBanknotes.putAll(tempBanknotes);
  }
}
