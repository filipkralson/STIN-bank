package cz.tul.kral.bank.model;

import java.math.BigDecimal;

public class CurrencyExchangeRate {

    private double exchangeRate;

    private String currencyCode;
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


    @Override
    public String toString() {
        return String.format(" %d %s %.2f\n", this.amount, this.currencyCode, this.exchangeRate);
    }
}

