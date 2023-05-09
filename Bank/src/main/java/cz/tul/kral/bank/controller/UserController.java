package cz.tul.kral.bank.controller;

import cz.tul.kral.bank.model.User;
import cz.tul.kral.bank.repo.UserRepository;
import cz.tul.kral.bank.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JavaMailSender javaMailSender;

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
            SimpleMailMessage email = new SimpleMailMessage();
            int code = 1234;
            email.setFrom("banknvb@email.cz");
            email.setTo(user.getEmail());
            email.setSubject("Naše/Vaše Banka - verifikace uživatele");
            email.setText(String.valueOf(code));
            javaMailSender.send(email);
            session.setAttribute("code", code);
            session.setAttribute("user", user);
            return "redirect:/verification";
        }
        return null;
    }

    @PostMapping("/verification")
    public String processVerification(@RequestParam("verification") int code, Model model, HttpSession session) {
        int codeCheck = (Integer) session.getAttribute("code");
        if (code == codeCheck) {
            return "redirect:/home";
        }
        model.addAttribute("warnLogin", "Zadali jste špatně kód!");
        return "verification";
    }
}
