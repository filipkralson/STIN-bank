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
        model.addAttribute("clientNum", user.getClientNum());
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

    @PostMapping("/register")
    public String processRegister(@RequestParam("name") String name, @RequestParam("surname") String surname, @RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        if ((name.equals("") && surname.equals("") && email.equals("") && password.equals(""))) {
            model.addAttribute("warningRegister","Zadejte všechny hodnoty!");
        } else {
            return "redirect:/login";
        }
        return null;
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("clientNum") int clientNum, @RequestParam("password") String password, Model model) {
        users.add(new User(321, "Tonda", "Svačina", "tonda.svacina@gmail.com", "123"));
        for (User user : users) {
            if (!(user.getPassword().equals(password)) || (user.getClientNum() != clientNum)) {
                model.addAttribute("warningLogin","Zadejte všechny hodnoty!");
            } else {
                return "redirect:/home";
            }
        }
        return null;
    }
}

