package cz.tul.kral.bank.model;

import jakarta.persistence.*;

@Entity(name = "CurrencyExchangeRates")
public class CurrencyExchangeRate {

    @Id
    private String currencyCode;

    private double exchangeRate;

    private int amount;

    public CurrencyExchangeRate() {

    }

    public int getAmount() {
        return amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public void setAmount(int i) {
        this.amount = i;
    }

}

