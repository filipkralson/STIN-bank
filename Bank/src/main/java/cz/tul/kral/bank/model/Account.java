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

    public Account() {

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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double _balance) {
        this.balance += _balance;
    }

    public Boolean pay(double amount){
        if((this.balance - amount) >= 0){
            this.balance -= amount;
            return true;
        }else{
            return false;
        }
    }

}
