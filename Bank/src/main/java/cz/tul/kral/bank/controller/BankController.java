package cz.tul.kral.bank.controller;

import cz.tul.kral.bank.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Controller
public class BankController {

    @GetMapping("/home")
    public String showHomePage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("name", user.getFirstName());
        model.addAttribute("surname", user.getLastName());
        model.addAttribute("id", user.getId());
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
        session.setAttribute("user", null);
        return "redirect:/login";
    }
}

