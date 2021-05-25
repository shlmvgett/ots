package homework;

import homework.models.BankCard;
import homework.models.Banknote;
import homework.models.BanknotesBox;

public class AtmImpl implements Atm {

  private final BanknotesBox banknotesBox;
  private BankCard currentCard;

  public AtmImpl(BanknotesBox banknotesBox) {
    System.out.println("Brrr... ATM was init: with banknotes");
    this.banknotesBox = banknotesBox;
  }

  @Override
  public Atm insertCard(BankCard card) {
    if (currentCard != null) {
      throw new RuntimeException("Eject previous card before using.");
    }
    this.currentCard = card;
    System.out.println("ATM: Card was inserted: " + card.getCardNumber());
    return this;
  }

  @Override
  public void ejectCard() {
    this.currentCard = null;
    System.out.println("ATM: Card was ejected. Come again!");
  }

  @Override
  public Atm putMoney(Banknote banknote) {
    System.out.println("ATM: Add money...");
    currentCard.putMoney(Long.valueOf(banknote.getIntValue()), banknote.getCurrency());
    banknotesBox.putMoney(banknote);
    return this;
  }

  @Override
  public Atm getMoney(Long amount) {
    System.out.println("ATM: Get money...");
    if (!currentCard.getAccount().hasAmount(amount)) {
      throw new RuntimeException("Not enough money on your account.");
    }
    currentCard.getMoney(amount);
    banknotesBox.getMoney(amount, currentCard.getAccount().getCurrency());
    return this;
  }

  @Override
  public Atm displayBalance() {
    System.out.printf("Current balance is: %s for card: %s%n",
        currentCard.getReadableAmount(), currentCard.getCardNumber());
    return this;
  }
}
