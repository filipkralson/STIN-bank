package cz.tul.kral.bank.controller;

import cz.tul.kral.bank.model.Account;
import cz.tul.kral.bank.model.User;
import cz.tul.kral.bank.repo.AccountRepository;
import cz.tul.kral.bank.repo.UserRepository;
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
    private UserRepository userRepository;

    @PostMapping("/create-account")
    public String processCreateAccount(@RequestParam("currency") String currency, HttpSession session) {
        Account accountNew = new Account();
        User user = (User) session.getAttribute("user");
        accountNew.setCurrency(currency);
        accountNew.setBalance(0);
        User userSession = userRepository.findById(user.getId());
        if (userSession != null) {
            accountNew.setUser(userSession);
            userSession.getAccounts().add(accountNew);
            accountRepository.save(accountNew);
            userRepository.save(userSession);
            return "redirect:/home";
        }
        return null;
    }
}