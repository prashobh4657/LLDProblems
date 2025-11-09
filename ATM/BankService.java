package ATM;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ATM.entities.Account;
import ATM.entities.Card;

public class BankService {
    private final Map<String, Account> accounts = new ConcurrentHashMap<>(); 
    private final Map<String, Card> cards = new ConcurrentHashMap<>(); //Card Number to Card Object Mapping
    private final Map<Card, Account> cardAccountMap = new ConcurrentHashMap<>();
    public BankService(){
        // Create sample accounts and cards
        Account account1 = createAccount("1234567890", 1000.0);
        Card card1 = createCard("1234-5678-9012-3456", 2345);
        linkCardToAccount(card1, account1);

        Account account2 = createAccount("9876543210", 500.0);
        Card card2 = createCard("9876-5432-1098-7654", 4321);
        linkCardToAccount(card2, account2);

    }
    public Card getCard(String cardNumber) {
        return cards.getOrDefault(cardNumber, null);
    }


    public Account createAccount(String accountNumber, double initialBalance) {
        Account account = new Account(accountNumber, initialBalance);
        accounts.put(accountNumber, account);
        return account;
    }

    public Card createCard(String cardNumber, int pin) {
        Card card = new Card(cardNumber, pin);
        cards.put(cardNumber, card);
        return card;
    }

    public void linkCardToAccount(Card card, Account account) {
        account.getLinkedCards().put(card.getCardNumber(), card);
        cardAccountMap.put(card, account);
    }

    public boolean authenticate(Card currentCard, int pin) {
        return currentCard.getPin() == pin;
    }
}
