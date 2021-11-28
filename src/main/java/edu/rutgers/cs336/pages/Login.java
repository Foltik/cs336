package edu.rutgers.cs336.pages;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.rutgers.cs336.services.UserSvc;
import edu.rutgers.cs336.services.UserSvc.User;

@Controller
@RequestMapping("/login")
public class Login {
    @Autowired
    private UserSvc users;

    @GetMapping
    public String index(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));
        return "login";
    }

    @PostMapping
    public String login(@ModelAttribute User form, HttpSession session, Model model) {
        users.login(form).ifPresentOrElse(user -> {
            // login successful
            session.setAttribute("user", user);
            model.addAttribute("message", "Login success!");
        }, () -> {
            // login failed
            model.addAttribute("message", "Login failed!");
        });


        return index(session, model);
    }

    @PutMapping
    public String register(@ModelAttribute User form, HttpSession session, Model model) {
        users.register(form).ifPresentOrElse(user -> {
            session.setAttribute("user", user);
            model.addAttribute("message", "Successfully registered user!");

        }, () -> {
            // User already exists
            model.addAttribute("message", "User already exists");
        });
        return index(session, model);
    }

    @DeleteMapping
    public String delete(HttpSession session, Model model) {
        session.removeAttribute("user");
        model.addAttribute("message", "Logged out.");
        return index(session, model);
    }
}
