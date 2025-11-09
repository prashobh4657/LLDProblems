package ATM.state;

import ATM.ATMSystem;
import ATM.OperationType;
import ATM.entities.Card;
import java.util.Objects;

public class IdleState implements ATMState {

    private ATMSystem atmSystem;

    public IdleState(ATMSystem atmSystem) {
        this.atmSystem = atmSystem;
    }

    @Override
    public void insertCard(String cardNumber) {
        System.out.println("Card inserted. Transitioning to CardInsertedState.");
        atmSystem.setCurrentState(new HasCardState(atmSystem));

        Card card = atmSystem.getCard(cardNumber);

        if (Objects.isNull(card)) {
            System.out.println("Card Number Not Found. Ejecting card.");
            atmSystem.ejectCard();
        } else {
            atmSystem.setCurrentCard(card);
        }
    }

    @Override
    public void ejectCard() {
        System.out.println("No card inserted. ATM is already in idle state.");
    }

    @Override
    public void enterPin(int pin) {
        System.out.println("No card inserted. Please insert a card first.");

    }

    @Override
    public void selectOperation(OperationType operationType, int... args) {
        System.out.println("No card inserted. Please insert a card first.");

    }
}
