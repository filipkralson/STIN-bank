package cz.tul.kral.bank.controller;

import cz.tul.kral.bank.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class BankController {

    private List<User> users = new ArrayList<>();

    @GetMapping("/home")
    public String showHomePage(Model model) {
        User user = users.get(0);
        model.addAttribute("name",user.getFirstName());
        model.addAttribute("surname",user.getLastName());
        model.addAttribute("clientNum", user.getId());
        return "home";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("currentUser", null);
        return "redirect:/login";
    }
}

