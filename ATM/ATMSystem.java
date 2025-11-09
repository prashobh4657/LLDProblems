package ATM;

import ATM.entities.*;
import ATM.state.ATMState;
import ATM.state.IdleState;

//Context Class;
public class ATMSystem {
    private static ATMSystem INSTANCE;
    private ATMState currentState;
    private BankService bankService;
    private Card currentCard;

    private ATMSystem() {
        this.currentState = new IdleState(this);
        this.currentCard = null;
        this.bankService = new BankService();

        // Setup cash Dispensers;
    }

    public static ATMSystem getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ATMSystem();
        }
        return INSTANCE;
    }

    public void insertCard(String string) {
        currentState.insertCard(string);
    }

    public void enterPin(int pin) {
        currentState.enterPin(pin);
    }

    public void ejectCard() {
        currentState.ejectCard();
    }

    public Card getCard(String cardNumber) {
        return bankService.getCard(cardNumber);
    }

    public void setCurrentState(ATMState state) {
        this.currentState = state;
    }

    public ATMState getCurrentState() {
        return this.currentState;
    }

    public void setCurrentCard(Card card) {
        this.currentCard = card;
    }

    public boolean authenticate(int pin) {
        return bankService.authenticate(currentCard, pin);
    }
}
