package cz.tul.kral.bank.controller;

import cz.tul.kral.bank.model.Account;
import cz.tul.kral.bank.model.Transaction;
import cz.tul.kral.bank.model.User;
import cz.tul.kral.bank.repo.TransactionRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @PostMapping("/transaction-deposit")
    public String processDeposit(@RequestParam("insert") double insert, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Account account = user.getAccounts().get(0);
        Transaction transaction = new Transaction();
        transaction.setValue(insert);
        transaction.setType("vklad");
        transaction.setAccount(account);
        account.getTransactions().add(transaction);
        transactionRepository.save(transaction);
        return "redirect:/home";
    }

    @PostMapping("/transaction-pay")
    public String processPay() {
        return null;
    }
}
