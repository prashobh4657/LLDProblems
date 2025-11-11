package ATM.state;

import ATM.ATMSystem;
import ATM.OperationType;

public class AuthenticatedState implements ATMState {
    private ATMSystem atmSystem; // Reference Of Context Class

    public AuthenticatedState(ATMSystem atmSystem) {
        this.atmSystem = atmSystem;
    }

    @Override
    public void insertCard(String cardNumber) {
        System.out.println("Error: A card is already inserted and a session is active.");
    }

    @Override
    public void ejectCard() {
        System.out.println("Ending session. Card has been ejected. Thank you for using our ATM.");
        atmSystem.setCurrentCard(null);
        atmSystem.setCurrentState(new IdleState(atmSystem));
    }

    @Override
    public void enterPin(int pin) {
        System.out.println("Error: PIN has already been entered and authenticated.");
    }

    @Override
    public void selectOperation(OperationType operationType, int... args) {
        switch (operationType) {
            case CHECK_BALANCE:
                System.out.println("Checking balance...");
                atmSystem.checkBalance();
                break;
            case WITHDRAW_CASH:
                System.out.println("Withdrawing cash...");
                atmSystem.withdrawCash(args[0]);
                // Logic to withdraw cash
                break;
            case DEPOSIT_CASH:
                System.out.println("Depositing cash...");
                atmSystem.depositCash(args[0]);
                // Logic to deposit cash
                break;
            default:
                System.out.println("Invalid operation selected.");
        }

        // End the session after one transaction
        System.out.println("Transaction complete.");
        atmSystem.ejectCard();
    }

}
