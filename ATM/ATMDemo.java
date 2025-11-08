package ATM;
import ATM.ATMSystem;

public class ATMDemo {
    public static void run() {
        System.out.println("Starting ATM Demo...");
        ATMSystem atmSystem = ATMSystem.getInstance();
        System.out.println("Ending ATM Demo...");
    }
}
