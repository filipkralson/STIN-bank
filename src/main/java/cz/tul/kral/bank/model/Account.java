package cz.tul.kral.bank.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String currency;
    private double balance;
    @ManyToOne()
    @JoinColumn(referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();


    public Account(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public String getBalanceRounded() {
        return String.format("%.2f", balance);
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account() {

    }

    public Account(int id, String currency, double balance) {
        this.id = id;
        this.currency = currency;
        this.balance = balance;
    }
    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Transaction transaction){
        this.transactions.add(transaction);
    }

    public int getId() {
        return id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String _currency) {
        this.currency = _currency;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setBalance(double _balance) {
        this.balance += _balance;
    }

    public void pay(double amount) throws Exception {
        if((this.balance - amount) >= 0){
            this.balance -= amount;
        } else {
            throw new Exception();
        }
    }
}
