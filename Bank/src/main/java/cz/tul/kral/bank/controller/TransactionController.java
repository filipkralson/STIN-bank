package cz.tul.kral.bank.controller;

import cz.tul.kral.bank.model.Account;
import cz.tul.kral.bank.model.CurrencyExchangeRate;
import cz.tul.kral.bank.model.Transaction;
import cz.tul.kral.bank.service.AccountService;
import cz.tul.kral.bank.service.CurrencyExchangeRateService;
import cz.tul.kral.bank.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
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
    public String deposit(@RequestParam("insert") double insert, HttpServletRequest request, HttpSession session) {
        String currency = request.getParameter("currency");
        int id = Integer.parseInt((String) session.getAttribute("idUser"));
        Account account = accountService.getAccountById(id);
        Transaction transaction = new Transaction();
        transaction.setType("Vklad");
        transaction.setAccount(account);
        CurrencyExchangeRate currencyExchangeRate = currencyExchangeRateService.getExchangeRateByCode(currency);
        CurrencyExchangeRate conversionMaker = currencyExchangeRateService.getExchangeRateByCode(account.getCurrency());

        if (account.getCurrency().equals(currency)) {
            transaction.setValue(insert);
            account.setBalance(insert);
        } else if ((account.getCurrency().equals("CZK"))) {
            double czkInsert = currencyExchangeRate.getExchangeRate() * insert;
            transaction.setValue(czkInsert);
            account.setBalance(czkInsert);
        } else if ((currency.equals("CZK")) && !(account.getCurrency().equals("CZK"))) {
            double czkInsert = insert / conversionMaker.getExchangeRate();
            transaction.setValue(czkInsert);
            account.setBalance(czkInsert);
        } else {
            double czkInsert = insert * currencyExchangeRate.getExchangeRate();
            double conversion = conversionMaker.getExchangeRate();
            double convertedAmount = czkInsert / conversion;
            transaction.setValue(convertedAmount);
            account.setBalance(convertedAmount);
        }
        account.setTransactions(transaction);
        transactionService.createTransaction(transaction);
        accountService.createAccount(account);
        session.removeAttribute("idUser");
        return "redirect:/home";
    }

    @PostMapping("/transaction-pay")
    public String pay(@RequestParam("insert") double amount, HttpServletRequest request, @RequestParam("recipient_account") String recipient_account,HttpSession session, Model model){
        String currency = request.getParameter("currency");
        int id = Integer.parseInt((String) session.getAttribute("idUser"));
        Account account = accountService.getAccountById(id);
        Transaction transaction = new Transaction();
        transaction.setType("Platba");
        transaction.setRecipient_account(Integer.parseInt(recipient_account));
        transaction.setAccount(account);
        CurrencyExchangeRate currencyExchangeRate = currencyExchangeRateService.getExchangeRateByCode(currency);
        CurrencyExchangeRate conversionMaker = currencyExchangeRateService.getExchangeRateByCode(account.getCurrency());

        if(account.getCurrency().equals(currency)) {
            transaction.setValue(amount);
            account.pay(amount);
        } else if ((currency.equals("CZK")) && !(account.getCurrency().equals("CZK"))) {
            double newAmount = amount / conversionMaker.getExchangeRate();
            transaction.setValue(newAmount);
            account.pay(newAmount);
        } else if ((account.getCurrency().equals("CZK"))) {
            double czkInsert = currencyExchangeRate.getExchangeRate() * amount;
            transaction.setValue(czkInsert);
            account.pay(czkInsert);
        } else {
            double czkInsert = amount * currencyExchangeRate.getExchangeRate();
            double conversion = conversionMaker.getExchangeRate();
            double convertedAmount = czkInsert / conversion;
            transaction.setValue(convertedAmount);
            account.pay(convertedAmount);
        }

        account.setTransactions(transaction);
        transactionService.createTransaction(transaction);
        accountService.createAccount(account);
        session.removeAttribute("idUser");
        return "redirect:/home";
        }

    @PostMapping("/deposit")
    public String processTransactionDeposit(@RequestParam("idUser") String id, HttpSession session){
        session.setAttribute("idUser", id);
        return "redirect:/transaction-deposit";
    }

    @PostMapping("/pay")
    public String processTransactionPay(@RequestParam("idUser") String id, HttpSession session){
        session.setAttribute("idUser", id);
        return "redirect:/transaction-pay";
    }

    @PostMapping("/transactions")
    public String processTransactions(@RequestParam("idUser") String id, HttpSession session){
        session.setAttribute("idUser", id);
        return "redirect:/transactions";
    }

    public void setAccountService(AccountService _accountService) {
        accountService = _accountService;
    }

    public void setTransactionService(TransactionService _transactionService) {
        transactionService = _transactionService;
    }
}
