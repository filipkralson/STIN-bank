package cz.tul.kral.bank.controller;

import cz.tul.kral.bank.model.Account;
import cz.tul.kral.bank.model.User;
import cz.tul.kral.bank.repo.AccountRepository;
import cz.tul.kral.bank.repo.UserRepository;
import cz.tul.kral.bank.service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create-account")
    public String processCreateAccount(@RequestParam("currency") String currency, HttpSession session) {
        Account accountNew = new Account();
        accountNew.setCurrency(currency);
        accountNew.setBalance(0);
        accountService.createAccount(accountNew);
        User user = (User) session.getAttribute("user");
        if(user != null) {
            List<Account> accounts = user.getAccounts();
            if (accounts == null) {
                accounts = new ArrayList<>();
            }
            accountNew.setUser(user);
            user.getAccounts().add(accountNew);
            user.setAccounts(accounts);
            userRepository.save(user);
            return "redirect:/home";
            }
        return null;
    }
}
