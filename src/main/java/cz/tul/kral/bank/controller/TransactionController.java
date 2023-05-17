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
        int id = Integer.parseInt((String) session.getAttribute("idAcc"));
        Account account = accountService.getAccountById(id);
        Transaction transaction = new Transaction();
        transaction.setType("Vklad");
        transaction.setAccount(account);
        CurrencyExchangeRate currencyExchangeRate = currencyExchangeRateService.getExchangeRateByCode(currency);
        CurrencyExchangeRate conversionMaker = currencyExchangeRateService.getExchangeRateByCode(account.getCurrency());

        if (account.getCurrency().equals(currency)) {
            transaction.setValue(insert);
            account.setBalance(insert);
        } else if ((account.getCurrency().equals("CZK")) && !(currency.equals("CZK"))) {
            double czkInsert = currencyExchangeRate.getExchangeRate() * (insert / currencyExchangeRate.getAmount());
            transaction.setValue(czkInsert);
            account.setBalance(czkInsert);
        } else if ((currency.equals("CZK")) && !(account.getCurrency().equals("CZK"))) {
            double czkInsert = (insert / conversionMaker.getExchangeRate()) * conversionMaker.getAmount();
            transaction.setValue(czkInsert);
            account.setBalance(czkInsert);
        } else {
            double czkInsert = (insert / currencyExchangeRate.getAmount()) * currencyExchangeRate.getExchangeRate();
            double conversion = conversionMaker.getExchangeRate() * ((double) 1 / conversionMaker.getAmount());
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
        int id = Integer.parseInt((String) session.getAttribute("idAcc"));
        Account account = accountService.getAccountById(id);
        Transaction transaction = new Transaction();
        transaction.setType("Platba");
        transaction.setRecipient_account(Integer.parseInt(recipient_account));
        transaction.setAccount(account);
        CurrencyExchangeRate currencyExchangeRate = currencyExchangeRateService.getExchangeRateByCode(currency);
        CurrencyExchangeRate conversionMaker = currencyExchangeRateService.getExchangeRateByCode(account.getCurrency());

        if(account.getCurrency().equals(currency)) {
            transaction.setValue(amount);
            try {
                account.pay(amount);
            } catch (Exception e) {
                model.addAttribute("error", "Nedostatek prostředků");
                return "transaction-pay";
            }
        } else if ((currency.equals("CZK")) && !(account.getCurrency().equals("CZK"))) {
            double newAmount = (amount / conversionMaker.getExchangeRate()) * conversionMaker.getAmount();
            transaction.setValue(newAmount);
            try {
                account.pay(newAmount);
            } catch (Exception e) {
                model.addAttribute("error", "Nedostatek prostředků");
                return "transaction-pay";
            }
        } else if ((account.getCurrency().equals("CZK")) && !(currency.equals("CZK"))) {
            double czkInsert = currencyExchangeRate.getExchangeRate() * (amount / currencyExchangeRate.getAmount());
            transaction.setValue(czkInsert);
            try {
                account.pay(czkInsert);
            } catch (Exception e) {
                model.addAttribute("error", "Nedostatek prostředků");
                return "transaction-pay";
            }
        } else {
            double czkInsert = (amount / currencyExchangeRate.getAmount())* currencyExchangeRate.getExchangeRate();
            double conversion = conversionMaker.getExchangeRate() * ((double) 1 / conversionMaker.getAmount());
            double convertedAmount = czkInsert / conversion;
            transaction.setValue(convertedAmount);
            try {
                account.pay(convertedAmount);
            } catch (Exception e) {
                model.addAttribute("error", "Nedostatek prostředků");
                return "transaction-pay";
            }
        }
        account.setTransactions(transaction);
        transactionService.createTransaction(transaction);
        accountService.createAccount(account);
        session.removeAttribute("idUser");
        return "redirect:/home";
        }

    @PostMapping("/deposit")
    public String processTransactionDeposit(@RequestParam("idAcc") String id, HttpSession session){
        session.setAttribute("idAcc", id);
        return "redirect:/transaction-deposit";
    }

    @PostMapping("/pay")
    public String processTransactionPay(@RequestParam("idAcc") String id, HttpSession session){
        session.setAttribute("idAcc", id);
        return "redirect:/transaction-pay";
    }

    @PostMapping("/transactions")
    public String processTransactions(@RequestParam("idAcc") String id, HttpSession session){
        session.setAttribute("idAcc", id);
        return "redirect:/transactions";
    }

    public void setAccountService(AccountService _accountService) {
        accountService = _accountService;
    }

    public void setTransactionService(TransactionService _transactionService) {
        transactionService = _transactionService;
    }

    public void setCurrencyExchangeRateService(CurrencyExchangeRateService currencyExchangeRateService) {
        this.currencyExchangeRateService = currencyExchangeRateService;
    }
}
