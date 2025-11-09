package ATM.state;

import java.util.Objects;

import ATM.ATMSystem;
import ATM.OperationType;
import ATM.entities.Card;

public class IdleState implements ATMState {

    private ATMSystem atmSystem;

    public IdleState(ATMSystem atmSystem) {
        this.atmSystem = atmSystem;
    }

    @Override
    public void insertCard(String cardNumber) {
        System.out.println("Card inserted. Transitioning to CardInsertedState.");
        Card card = atmSystem.getCard(cardNumber);

        if (Objects.isNull(card)) {
            System.out.println("Card Number Not Found. Ejecting card.");
        } else {
            atmSystem.setCurrentCard(card);
            atmSystem.setCurrentState(new HasCardState(atmSystem));
        }
    }

    @Override
    public void ejectCard() {
        System.out.println("No card to eject. ATM is idle.");
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
