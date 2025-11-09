package ATM.entities;

import java.util.HashMap;
import java.util.Map;

public class Account {
    private String accountNumber;
    private double balance;
    private Map<String, Card> linkedCards; // Map of cardNumber to Card objects

    public Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.linkedCards = new HashMap<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public Map<String, Card> getLinkedCards() {
        return linkedCards;
    }

    public synchronized void deposit(double amount) {
        balance += amount;
    }

    public synchronized boolean withdraw(double amount) {
        if (amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }

}
