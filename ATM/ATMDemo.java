package ATM;

public class ATMDemo {
    public static void run() {
        System.out.println("Starting ATM Demo...");
        ATMSystem atmSystem = ATMSystem.getInstance();

        atmSystem.insertCard("1234-5678-9012-345");
        atmSystem.enterPin(23452);

        // atmSystem.insertCard("1234-5678-9012-3456");
        // atmSystem.enterPin(2345);

        // atmSystem.ejectCard();

        // System.out.println("Ending ATM Demo...");
    }
}
