package cz.tul.kral.bank.model;

public class Account {
    private String accountNumber;
    private User owner;
    private Currency currency;
    private double balance;

    public Account(String accountNumber, User owner, Currency currency) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.currency = currency;
        this.balance = 0;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public User getOwner() {
        return owner;
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getBalance() {
        return balance;
    }
    public void deposit(double amount) {
        balance += amount;
    }

    public void depositInCurrency(double amount, Currency currency, CurrencyConverter converter) {
        if (this.currency == currency) {
            deposit(amount);
        } else {
            double convertedAmount = converter.convert(amount);
            deposit(convertedAmount);
        }
    }

    public void withdraw(double amount) {
        balance -= amount;
    }

    public void withdrawInCurrency(double amount, Currency currency, CurrencyConverter converter) {
        if (this.currency == currency) {
            withdraw(amount);
        } else {
            double convertedAmount = converter.convert(amount);
            withdraw(convertedAmount);
        }
    }

    public double getBalanceInCurrency(Currency currency, CurrencyConverter converter) {
        if (this.currency == currency) {
            return getBalance();
        } else {
            return converter.convert(balance);
        }
    }

}
