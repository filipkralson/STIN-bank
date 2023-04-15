package cz.tul.kral.bank.inter;

import cz.tul.kral.bank.model.Currency;
import java.math.BigDecimal;


public abstract class IExchangeRateProvider {
    public abstract BigDecimal getExchangeRate(Currency sourceCurrency, Currency targetCurrency);
}
