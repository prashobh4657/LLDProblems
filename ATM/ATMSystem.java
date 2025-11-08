package ATM;

import ATM.state.ATMState;

public class ATMSystem {
    private static ATMSystem INSTANCE;
    private ATMState currentState;
    
    public static ATMSystem getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ATMSystem();
        }
        return INSTANCE;
    }

}
