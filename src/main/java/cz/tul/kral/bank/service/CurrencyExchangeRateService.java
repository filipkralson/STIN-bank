package cz.tul.kral.bank.service;

import cz.tul.kral.bank.model.CurrencyExchangeRate;
import cz.tul.kral.bank.repo.CurrencyExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

@Service
public class CurrencyExchangeRateService {

    private static final String CNB_URL = "https://www.cnb.cz/cs/financni-trhy/devizovy-trh/kurzy-devizoveho-trhu/kurzy-devizoveho-trhu/denni_kurz.txt";
    private static final DecimalFormat DECIMAL_FORMAT;

    @Autowired
    private CurrencyExchangeRateRepository currencyExchangeRateRepository;

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        DECIMAL_FORMAT = new DecimalFormat("#,##0.00", symbols);
    }

    public void updateExchangeRates() throws IOException, ParseException {
        drop();
        URL url = new URL(CNB_URL);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            reader.readLine();
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split("\\|");
                CurrencyExchangeRate exchangeRate = new CurrencyExchangeRate();
                exchangeRate.setAmount(Integer.parseInt(columns[2]));
                exchangeRate.setCurrencyCode(columns[3]);
                exchangeRate.setExchangeRate(DECIMAL_FORMAT.parse(columns[4]).doubleValue());
                createCurrecy(exchangeRate);
            }
        }
    }

    public void drop() {
        currencyExchangeRateRepository.deleteAll();
    }

    public CurrencyExchangeRate createCurrecy(CurrencyExchangeRate currencyExchangeRate) {
        return currencyExchangeRateRepository.save(currencyExchangeRate);
    }

    public CurrencyExchangeRate getExchangeRateByCode(String code){
        return currencyExchangeRateRepository.findByCurrencyCode(code);
    }

}