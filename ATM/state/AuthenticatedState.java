package ATM.state;

import ATM.ATMSystem;
import ATM.OperationType;

public class AuthenticatedState implements ATMState {
    private ATMSystem atmSystem; //Reference Of Context Class

    public AuthenticatedState(ATMSystem atmSystem) {
        this.atmSystem = atmSystem;
    }

    @Override
    public void insertCard(String cardNumber) {
        System.out.println("Card already inserted and authenticated.");
    }

    @Override
    public void ejectCard() {
        System.out.println("Ejecting card. Transitioning to IdleState.");
    }

    @Override
    public void enterPin(int pin) {
        System.out.println("PIN already entered and authenticated.");
    }

    @Override
    public void selectOperation(OperationType operationType, int... args) {
        System.out.println("Withdrawing cash: " + args[0]);
    }

}
