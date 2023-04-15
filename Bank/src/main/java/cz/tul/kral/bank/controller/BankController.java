package cz.tul.kral.bank.controller;

import cz.tul.kral.bank.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class BankController {

    private List<User> users = List.of(new User("Tonda", "Svacina", "tonda.svacina@gmail.com"));

    @GetMapping("/users")
    public List<User> get() {
        return users;
    }

    @GetMapping("/")
    public String hello() {
        return "Hello bank user!";
    }
}
