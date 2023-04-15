package cz.tul.kral.bank.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionManager {
    private Map<Account, List<Payment>> transactions = new HashMap<>();

    public void addTransaction(Payment payment) {
        Account sender = payment.getSenderAccount();
        Account receiver = payment.getReceiverAccount();

        // Add the transaction to the sender's transaction history
        List<Payment> senderTransactions = transactions.getOrDefault(sender, new ArrayList<>());
        senderTransactions.add(payment);
        transactions.put(sender, senderTransactions);

        // Add the transaction to the receiver's transaction history
        List<Payment> receiverTransactions = transactions.getOrDefault(receiver, new ArrayList<>());
        receiverTransactions.add(payment);
        transactions.put(receiver, receiverTransactions);
    }

    public List<Payment> getTransactionsForAccount(Account account) {
        return transactions.getOrDefault(account, new ArrayList<>());
    }

    public List<Payment> getAllTransactions() {
        List<Payment> allTransactions = new ArrayList<>();
        transactions.values().forEach(allTransactions::addAll);
        return allTransactions;
    }
}


