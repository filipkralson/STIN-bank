package cz.tul.kral.bank.controller;

import cz.tul.kral.bank.model.CurrencyExchangeRate;
import cz.tul.kral.bank.service.CurrencyExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.text.ParseException;

@Controller
public class CurrencyExchangeRateController {

    @Autowired
    private CurrencyExchangeRateService currencyExchangeRateService;

    public CurrencyExchangeRateController(CurrencyExchangeRateService _currencyExchangeRateService) {
        this.currencyExchangeRateService = _currencyExchangeRateService;
    }

    @GetMapping("/currencies")
    public void getCurrencies(Model model) throws IOException, ParseException {
        model.addAttribute("currencies", currencyExchangeRateService.getExchangeRates());
    }

    @PostMapping("/transaction-deposit")
    public CurrencyExchangeRate getCurrencyByCode(@RequestParam String code) {
        return currencyExchangeRateService.getExchangeRateByCode(code);
    }

}
