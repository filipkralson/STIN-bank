package cz.tul.kral.bank.controller;

import cz.tul.kral.bank.model.Account;
import cz.tul.kral.bank.model.CurrencyExchangeRate;
import cz.tul.kral.bank.model.Transaction;
import cz.tul.kral.bank.service.AccountService;
import cz.tul.kral.bank.service.CurrencyExchangeRateService;
import cz.tul.kral.bank.service.TransactionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CurrencyExchangeRateService currencyExchangeRateService;

    @PostMapping("/transaction-deposit")
    public String deposit(@RequestParam("insert") double insert, @RequestParam("currency") String currency, HttpSession session) {
        int id = Integer.parseInt((String) session.getAttribute("idUcet"));
        double newBalance = 0;
        Account account = accountService.getAccountById(id);
        Transaction transaction = new Transaction();
        transaction.setType("Vklad");
        transaction.setAccount(account);
        CurrencyExchangeRate currencyExchangeRate = currencyExchangeRateService.getExchangeRateByCode(currency);
        if(account.getCurrency().equals(currencyExchangeRate.getCurrencyCode())) {
            transaction.setValue(insert);
            account.setBalance(insert);
            account.setTransactions(transaction);
        }
        newBalance = insert / currencyExchangeRate.getExchangeRate();
        transaction.setValue(newBalance);
        account.setBalance(newBalance);
        account.setTransactions(transaction);
        transactionService.createTransaction(transaction);
        accountService.createAccount(account);
        session.removeAttribute("idUcet");
        return "redirect:/home";
    }

    @PostMapping("/transaction-pay")
    public String pay(@RequestParam("insert") double amount, @RequestParam("currency") String currency, @RequestParam("recipient_account") String recipient_account,HttpSession session, Model model){
        int id = Integer.parseInt((String) session.getAttribute("idUcet"));
        Account account = accountService.getAccountById(id);
        double newAmount = 0;
        Transaction transaction = new Transaction();
        transaction.setType("Platba");
        transaction.setRecipient_account(Integer.parseInt(recipient_account));
        transaction.setAccount(account);
        CurrencyExchangeRate currencyExchangeRate = currencyExchangeRateService.getExchangeRateByCode(currency);
        if(account.getCurrency().equals(currencyExchangeRate.getCurrencyCode())) {
            if(!account.pay(amount)) {
                model.addAttribute("money", "Na účtu není dostatek peněz.");
                return "transaction-pay";
            }
            transaction.setValue(amount);
            account.pay(amount);
            account.setTransactions(transaction);
        }
        newAmount = amount * currencyExchangeRate.getExchangeRate();
        transaction.setValue(newAmount);
        account.pay(newAmount);
        account.setTransactions(transaction);
        transactionService.createTransaction(transaction);
        accountService.createAccount(account);
        session.removeAttribute("idUcet");
        return "redirect:/home";
        }

    @PostMapping("/deposit")
    public String processTransactionDeposit(@RequestParam("idUcet") String id, HttpSession session){
        session.setAttribute("idUcet", id);
        return "redirect:/transaction-deposit";
    }

    @PostMapping("/pay")
    public String processTransactionPay(@RequestParam("idUcet") String id, HttpSession session){
        session.setAttribute("idUcet", id);
        return "redirect:/transaction-pay";
    }

    @PostMapping("/transactions")
    public String processTransactions(@RequestParam("idUcet") String id, HttpSession session){
        session.setAttribute("idUcet", id);
        return "redirect:/transactions";
    }

    public void setAccountService(AccountService _accountService) {
        accountService = _accountService;
    }

    public void setTransactionService(TransactionService _transactionService) {
        transactionService = _transactionService;
    }
}
