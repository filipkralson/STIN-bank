package cz.tul.kral.bank.controller;

import cz.tul.kral.bank.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class BankController {

    private List<User> users = new ArrayList<>();

    @GetMapping("/home")
    public String showHomePage() {
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

    @PostMapping("/register")
    public String processRegister(@RequestParam("name") String name, @RequestParam("surname") String surname, @RequestParam("email") String email, @RequestParam("password") String password) {
        if ((name != null) && (surname != null )&&(email != null)&&(password != null)) {
            users.add(new User(321, name, surname, email, password));
            return "redirect:/login";
        } else {
            return "redirect:/register?error=true";
        }
    }


    @PostMapping("/login")
    public String processLogin(@RequestParam("clientNum") String clientNum, @RequestParam("password") String password) {
        List<User> users = new ArrayList<>();
        users.add(new User(123,"tonda","svacina","email@email.cz","123"));
        for (User user : users) {
            if (clientNum.equals(user.getClientNum()) && password.equals(user.getPassword())) {
                return "redirect:/home"; // Redirect to home page if login successful
            }
        }
        return "redirect:/login?error=true"; // Redirect back to login page with error parameter if login unsuccessful
    }
}

