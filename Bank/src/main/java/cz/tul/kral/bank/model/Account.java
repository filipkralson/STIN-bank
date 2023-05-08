package cz.tul.kral.bank.model;

import jakarta.persistence.*;

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

    public Account() {

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
        this.balance = _balance;
    }

}
