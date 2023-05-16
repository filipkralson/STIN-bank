package cz.tul.kral.bank.service;

import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;

@Component
@EnableScheduling
public class CurrencyExchangeRateScheduler {

    @Autowired
    private CurrencyExchangeRateService currencyExchangeRateService;

    @Scheduled(cron = "0 00 15 * * ?")
    public void updateCurrencyExchangeRates() throws IOException, ParseException {
        currencyExchangeRateService.updateExchangeRates();
    }
}
