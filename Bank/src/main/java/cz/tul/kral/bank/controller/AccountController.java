package cz.tul.kral.bank.controller;

import cz.tul.kral.bank.model.Account;
import cz.tul.kral.bank.model.User;
import cz.tul.kral.bank.repo.AccountRepository;
import cz.tul.kral.bank.repo.UserRepository;
import cz.tul.kral.bank.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserService userService;


    @PostMapping("/create-account")
    public String processCreateAccount(@RequestParam("currency") String currency, HttpSession session) {
        Account accountNew = new Account();
        User user = userService.getUserById(Integer.parseInt(session.getAttribute("user").toString()));
        accountNew.setCurrency(currency);
        accountNew.setBalance(0);
        if (user != null) {
            accountNew.setUser(user);
            user.setAccounts(accountNew);
            accountRepository.save(accountNew);
            userService.createUser(user);
            return "redirect:/home";
        }
        return null;
    }
}