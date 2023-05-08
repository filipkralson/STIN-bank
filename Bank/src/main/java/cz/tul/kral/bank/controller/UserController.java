package cz.tul.kral.bank.controller;

import cz.tul.kral.bank.model.User;
import cz.tul.kral.bank.repo.UserRepository;
import cz.tul.kral.bank.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public String processRegister(@RequestParam("name") String name, @RequestParam("surname") String surname, @RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        if ((name.equals("") && surname.equals("") && email.equals("") && password.equals(""))) {
            model.addAttribute("warningRegister","Zadejte všechny hodnoty!");
        } else {
            User user = new User();
            user.setFirstName(name);
            user.setLastName(surname);
            user.setEmail(email);
            user.setPassword(password);
            userService.createUser(user);
            return "redirect:/login";
        }
        return null;
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("id") int id, @RequestParam("password") String password, Model model, HttpSession session) {
        User user = userService.getUserById(id);
        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("warningLogin","Špatně zadané údaje!");
        } else {
            session.setAttribute("user", user);
            return "redirect:/home";
        }
        return null;
    }
}
