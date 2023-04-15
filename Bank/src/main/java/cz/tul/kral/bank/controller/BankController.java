package cz.tul.kral.bank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankController {

    @GetMapping("/")
    public String hello() {
        return "Hello bank user!";
    }
}
