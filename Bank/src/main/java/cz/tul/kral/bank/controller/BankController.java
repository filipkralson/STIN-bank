package cz.tul.kral.bank.controller;

import cz.tul.kral.bank.model.Account;
import cz.tul.kral.bank.model.User;
import cz.tul.kral.bank.service.AccountService;
import cz.tul.kral.bank.service.CurrencyExchangeRateService;
import cz.tul.kral.bank.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

@Controller
public class BankController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String showHomePage(Model model, HttpSession session) {
        int idUser = Integer.parseInt(session.getAttribute("user").toString());
        User user = userService.getUserById(idUser);
        model.addAttribute("name", user.getFirstName());
        model.addAttribute("surname", user.getLastName());
        model.addAttribute("id", user.getId());
        model.addAttribute("accounts", user.getAccounts());
        return "home";
    }

    @GetMapping("/")
    public String showLoginPageRedirect() {
        return "redirect:/login";
    }

    @GetMapping("/create-account")
    public String showCreateAccount() {
        return "create-account";
    }

    @GetMapping("/createAcc")
    public String showCreateAccountRedirect() {
        return "redirect:/create-account";
    }

    @GetMapping("/verification")
    public String showVerificationPage() {
        return "verification";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("user", null);
        return "redirect:/login";
    }

    @GetMapping("/transaction-deposit")
    public String showDeposit() {
        return "transaction-deposit";
    }

    @GetMapping("/transaction-pay")
    public String showPay() {
        return "transaction-pay";
    }

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "userId", required = false) Integer userId, Model model){
        if(userId != null){
            model.addAttribute("userId", userId);
        }
        return "login";
    }

    @GetMapping("/transactions")
    public String showLogPage(HttpSession session, Model model){
        int id = Integer.parseInt(session.getAttribute("idAcc").toString());
        Account account = accountService.getAccountById(id);
        model.addAttribute("accNum", id);
        model.addAttribute("transactions", account.getTransactions());
        return "transactions";
    }

    @GetMapping("/back")
    public String showBack() {
        return "redirect:/home";
    }

}

