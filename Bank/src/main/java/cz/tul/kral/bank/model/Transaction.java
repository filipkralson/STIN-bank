package cz.tul.kral.bank.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "Transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String type;

    private int recipient_account;

    private double value;

    private Date transaction_date;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Account account;

    Transaction() {

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

    public Date getTransaction_date() {
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

    public void setTransaction_date(Date transaction_date) {
        this.transaction_date = transaction_date;
    }
}
