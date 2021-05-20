package homework.models;

public interface BanknotesBox {

  void putMoney(Banknote banknote);

  void getMoney(Long amount);
}
