package ATM;

import ATM.entities.*;
import ATM.service.BankService;
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

    // Change State Method ==>> Setter for ATMState
    public void setCurrentState(ATMState state) {
        this.currentState = state;
    }

    // Setter for Card
    public void setCurrentCard(Card card) {
        this.currentCard = card;
    }

    // Delegation Methods;
    public void insertCard(String string) {
        currentState.insertCard(string);
    }

    public void enterPin(int pin) {
        currentState.enterPin(pin);
    }

    public void ejectCard() {
        currentState.ejectCard();
    }

    public void selectOperation(OperationType op, int... args) {
        currentState.selectOperation(op, args);
    }

    // Context Class Methods; ==>> BankService Delegation Methods;
    /*
     * 
     * ATMState classes should not directly access BankService because it breaks
     * encapsulation and couples the state logic with the banking logic.
     * In a well-designed system, each component has a distinct responsibility —
     * ATMState handles user interactions and state transitions,
     * while BankService manages core banking operations. The ATMSystem acts as the
     * mediator between them, ensuring a clean separation of
     * concerns, better maintainability, and adherence to the State Design Pattern
     * principles.
     * 
     * If ATMState directly accessed BankService, then:
     * The state classes would start containing business logic, not just state
     * behavior.
     * It would violate encapsulation — the BankService’s internals would leak into
     * multiple states.
     * You’d end up with tight coupling (harder to maintain and test).
     * So instead, the ATMSystem (context) acts as a mediator between the two:
     * ATMState → ATMSystem → BankService
     * 
     */
    public Card getCard(String cardNumber) {
        return bankService.getCard(cardNumber);
    }

    public boolean authenticate(int pin) {
        return bankService.authenticate(currentCard, pin);
    }

    public void checkBalance() {
        double balance = bankService.getBalance(currentCard);
        System.out.printf("Your current account balance is: $%.2f%n", balance);
    }

    public void withdrawCash(int amount) {
        bankService.withdraw(currentCard, amount);

        try {
            cashDispenser.dispenseCash(amount);
        } catch (Exception e) {
            bankService.depositMoney(currentCard, amount); // Deposit back if dispensing fails
        }

    }

    public void depositCash(int amount) {
        bankService.deposit(currentCard, amount);
    }
}
