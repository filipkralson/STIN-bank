package cz.tul.kral.bank.service;

import cz.tul.kral.bank.model.CurrencyExchangeRate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class CurrencyExchangeRateService {

    private static final String CNB_URL = "https://www.cnb.cz/cs/financni-trhy/devizovy-trh/kurzy-devizoveho-trhu/kurzy-devizoveho-trhu/denni_kurz.txt";
    private static final DecimalFormat DECIMAL_FORMAT;

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        DECIMAL_FORMAT = new DecimalFormat("#,##0.0000", symbols);
    }

    private List<CurrencyExchangeRate> exchangeRates;
    private Date lastUpdated;

    public CurrencyExchangeRateService() {
        this.exchangeRates = new ArrayList<>();
    }

    public void updateExchangeRates() throws IOException, ParseException {
        URL url = new URL(CNB_URL);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            reader.readLine();
            reader.readLine();
            List<CurrencyExchangeRate> tempExchangeRates = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split("\\|");
                CurrencyExchangeRate exchangeRate = new CurrencyExchangeRate();
                exchangeRate.setAmount(Integer.parseInt(columns[2]));
                exchangeRate.setCurrencyCode(columns[3]);
                exchangeRate.setExchangeRate(DECIMAL_FORMAT.parse(columns[4]).doubleValue());
                tempExchangeRates.add(exchangeRate);
            }
            this.exchangeRates = tempExchangeRates;
            this.lastUpdated = new Date();
        }
    }

    public List<CurrencyExchangeRate> getExchangeRates() throws IOException, ParseException {
        return exchangeRates;
    }

    public CurrencyExchangeRate getExchangeRateByCode(String code){
        for (CurrencyExchangeRate rate : exchangeRates) {
            if (rate.getCurrencyCode().equalsIgnoreCase(code)) {
                return rate;
            }
        }
        return null;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }
}