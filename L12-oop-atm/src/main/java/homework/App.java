package homework;

import homework.models.Banknote;
import homework.models.IBankCard;
import homework.utils.AtmHelpers;

public class App {

  public static void main(String[] args) {
    IBankCard card = AtmHelpers.prepareCard();
    IAtm atm = AtmHelpers.prepareAtm();

    atm.insertCard(card)
        .displayBalance()
        .putMoney(new Banknote(1_000))
        .displayBalance()
        .getMoney(2_500L)
        .displayBalance()
        .ejectCard();
  }
}