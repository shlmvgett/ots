package homework;

import homework.models.Banknote;
import homework.models.BankCard;

public interface Atm {

  Atm insertCard(BankCard card);

  void ejectCard();

  Atm putMoney(Banknote banknote);

  Atm getMoney(Long amount);

  Atm displayBalance();
}
