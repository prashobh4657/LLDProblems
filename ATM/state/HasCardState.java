package ATM.state;

import ATM.ATMSystem;
import ATM.OperationType;

public class HasCardState implements ATMState {
    private ATMSystem atmSystem;

    public HasCardState(ATMSystem atmSystem) {
        this.atmSystem = atmSystem;
    }

    @Override
    public void insertCard(String cardNumber) {
        System.out.println("Error: A card is already inserted. Cannot insert another card.");
    }

    @Override
    public void ejectCard() {
        System.out.println("Card has been ejected. Thank you for using our ATM.");
        atmSystem.setCurrentCard(null);
        atmSystem.setCurrentState(new IdleState(atmSystem));
    }

    @Override
    public void enterPin(int pin) {
        System.out.println("PIN entered. Validating...");
        boolean isAuthenticated = atmSystem.authenticate(pin);

        if (isAuthenticated) {
            System.out.println("Authentication successful.");
            atmSystem.setCurrentState(new AuthenticatedState(atmSystem));
        } else {
            System.out.println("Authentication failed: Incorrect PIN.");
            atmSystem.ejectCard();
        }
    }

    @Override
    public void selectOperation(OperationType operationType, int... args) {
        System.out.println("Error: Please enter your PIN first to select an operation.");
    }

}
