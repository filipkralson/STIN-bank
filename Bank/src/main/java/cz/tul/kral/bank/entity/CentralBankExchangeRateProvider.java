package cz.tul.kral.bank.entity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import cz.tul.kral.bank.repository.IExchangeRateProvider;

public class CentralBankExchangeRateProvider extends IExchangeRateProvider {
    private Map<String, BigDecimal> exchangeRates = new HashMap<>();

    public CentralBankExchangeRateProvider() {
        // Initialize the exchange rates from the central bank API
        // ...
    }

    @Override
    public BigDecimal getExchangeRate(Currency sourceCurrency, Currency targetCurrency) {
        String key = sourceCurrency.getCode() + targetCurrency.getCode();
        BigDecimal rate = exchangeRates.get(key);
        if (rate == null) {
            throw new IllegalArgumentException("Exchange rate not found");
        }
        return rate;
    }
}
