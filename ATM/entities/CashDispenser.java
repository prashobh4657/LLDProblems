package ATM.entities;

import ATM.dispenseChain.DispenseChain;

public class CashDispenser {
    private DispenseChain dispenseChain;

    public CashDispenser(DispenseChain dispenseChain) {
        this.dispenseChain = dispenseChain;
    }

    public synchronized void dispenseCash(int amount) {
        dispenseChain.dispense(amount);
    }

    public synchronized boolean canDispenseCash(int amount) {
        if (amount % 10 != 0) {
            return false;
        }
        return dispenseChain.canDispense(amount);
    }

}
