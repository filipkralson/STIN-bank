package cz.tul.kral.bank.controller;

import cz.tul.kral.bank.model.Account;
import cz.tul.kral.bank.model.Transaction;
import cz.tul.kral.bank.service.AccountService;
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

    @PostMapping("/transaction-deposit")
    public String deposit(@RequestParam("insert") double insert, HttpSession session) {
        int id = Integer.parseInt((String) session.getAttribute("idUcet"));
        Account account = accountService.getAccountById(id);
        Transaction transaction = new Transaction("Vklad", insert, account);
        account.setBalance(insert);
        account.setTransactions(transaction);
        transactionService.createTransaction(transaction);
        accountService.createAccount(account);
        session.removeAttribute("idUcet");
        return "redirect:/home";
    }

    @PostMapping("/transaction-pay")
    public String pay(@RequestParam("insert") double amount, @RequestParam("recipient_account") String recipient_account,HttpSession session, Model model){
        int id = Integer.parseInt((String) session.getAttribute("idUcet"));
        Account account = accountService.getAccountById(id);
        Transaction transaction = new Transaction("Platba", amount, Integer.parseInt(recipient_account),account);
        if(!account.pay(amount)){
            model.addAttribute("money", "Na účtu není dostatek peněz.");
            return "transaction-pay";
        }else {
            account.setTransactions(transaction);
            transactionService.createTransaction(transaction);
            accountService.createAccount(account);
            session.removeAttribute("idUcet");
            return "redirect:/home";
        }
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
