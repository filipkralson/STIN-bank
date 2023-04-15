package cz.tul.kral.bank.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AccountManager {
    private Map<String, Account> accounts = new HashMap<>();

    public void addAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }

    public Account getAccountByNumber(String number) {
        return accounts.get(number);
    }

    public List<Account> getAccountsByUser(User user) {
        return accounts.values().stream()
                .filter(account -> account.getOwner().equals(user))
                .collect(Collectors.toList());
    }

    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }
}

