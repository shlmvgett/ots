package homework;

import homework.models.Banknote;
import homework.models.BankCard;
import homework.utils.AtmHelpers;

public class App {

  public static void main(String[] args) {
    BankCard card = AtmHelpers.prepareCard();
    Atm atm = AtmHelpers.prepareAtm();

    atm.insertCard(card)
        .displayBalance()
        .putMoney(new Banknote(1_000))
        .displayBalance()
        .getMoney(2_500L)
        .displayBalance()
        .ejectCard();
  }
}