package cz.tul.kral.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

    @GetMapping("/create-account")
    public String showCreateAccount() {
        return "create-account";
    }
}
