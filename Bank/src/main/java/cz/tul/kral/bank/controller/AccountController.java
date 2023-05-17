package cz.tul.kral.bank.controller;

import cz.tul.kral.bank.model.Account;
import cz.tul.kral.bank.model.User;
import cz.tul.kral.bank.service.AccountService;
import cz.tul.kral.bank.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;

    @PostMapping("/create-account")
    public String processCreateAccount(@RequestParam("currency") String currency, HttpSession session) {
        Account accountNew = new Account();
        int idUser = Integer.parseInt(session.getAttribute("user").toString());
        User user = userService.getUserById(idUser);
        accountNew.setCurrency(currency);
        accountNew.setBalance(0);
        if (user != null) {
            accountNew.setUser(user);
            user.setAccounts(accountNew);
            accountService.createAccount(accountNew);
            userService.createUser(user);
            return "redirect:/home";
        }
        return null;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}