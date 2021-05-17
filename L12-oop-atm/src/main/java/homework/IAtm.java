package homework;

import homework.models.Banknote;
import homework.models.IBankCard;

public interface IAtm {

  IAtm insertCard(IBankCard card);

  void ejectCard();

  IAtm putMoney(Banknote banknote);

  IAtm getMoney(Long amount);

  IAtm displayBalance();
}
