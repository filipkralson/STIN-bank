package cz.tul.kral.bank.model;

import java.math.BigDecimal;

public class Currency {
    private String code;
    private String name;
    private BigDecimal exchangeRate;

    public Currency(String code, String name, BigDecimal exchangeRate) {
        this.code = code;
        this.name = name;
        this.exchangeRate = exchangeRate;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }
}

