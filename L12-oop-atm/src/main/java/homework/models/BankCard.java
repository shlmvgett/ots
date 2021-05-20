package homework.models;

import java.util.Currency;
import java.util.UUID;

public interface BankCard {

  UUID getCardNumber();

  String getCardHolder();

  Account getAccount();

  String getReadableAmount();

  void putMoney(Long amount, Currency currency);

  void getMoney(Long amount);
}
