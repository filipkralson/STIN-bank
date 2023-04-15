package cz.tul.kral.bank.repository;

import cz.tul.kral.bank.entity.Currency;
import java.math.BigDecimal;


public abstract class IExchangeRateProvider {
    public abstract BigDecimal getExchangeRate(Currency sourceCurrency, Currency targetCurrency);
}
