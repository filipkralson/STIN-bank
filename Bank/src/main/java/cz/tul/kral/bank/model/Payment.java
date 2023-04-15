package cz.tul.kral.bank.model;


public class Payment {
    private Account senderAccount;
    private Account receiverAccount;
    private double amount;

    public Payment(Account senderAccount, Account receiverAccount, double amount) {
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.amount = amount;
    }

    public Account getSenderAccount() {
        return senderAccount;
    }

    public Account getReceiverAccount() {
        return receiverAccount;
    }

    public double getAmount() {
        return amount;
    }
}

