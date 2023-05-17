package cz.tul.kral.bank.repo;

import cz.tul.kral.bank.model.CurrencyExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyExchangeRateRepository extends JpaRepository<CurrencyExchangeRate, String> {
    CurrencyExchangeRate findByCurrencyCode(String currencyCode);


}
