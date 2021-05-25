package homework.models;

import java.util.Currency;

public interface BanknotesBox {

  void putMoney(Banknote banknote);

  void getMoney(Long amount, Currency currency);
}
