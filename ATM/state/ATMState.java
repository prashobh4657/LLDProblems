package ATM.state;

public interface ATMState {
    void insertCard();

    void ejectCard();

    void enterPin(int pin);

    void withdrawCash(double amount);
}