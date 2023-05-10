package cz.tul.kral.bank.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "Transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String type;

    private int recipient_account;

    private double value;

    private LocalDateTime transaction_date;



    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Account account;

    public Transaction() {

    }

    public Transaction(String _type, double _value, Account account){
        this.type = _type;
        this.value = _value;
        this.account = account;
        this.transaction_date = LocalDateTime.now();
    }

    public Transaction(String _type, double _value, int _recipient_account, Account account){
        this.type = _type;
        this.value = _value;
        this.recipient_account = _recipient_account;
        this.account = account;
        this.transaction_date = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getRecipient_account() {
        return recipient_account;
    }

    public double getValue() {
        return value;
    }

    public LocalDateTime getTransaction_date() {
        return transaction_date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRecipient_account(int recipient_account) {
        this.recipient_account = recipient_account;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setTransaction_date(LocalDateTime transaction_date) {
        this.transaction_date = transaction_date;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
