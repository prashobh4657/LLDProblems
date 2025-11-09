package ATM.state;

import ATM.OperationType;

public interface ATMState {
    void insertCard(String cardNumber);

    void ejectCard();

    void enterPin(int pin);

    void selectOperation(OperationType operationType, int... args);
}