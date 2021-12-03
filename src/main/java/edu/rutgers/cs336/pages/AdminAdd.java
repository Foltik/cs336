package edu.rutgers.cs336.pages;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
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
import edu.rutgers.cs336.services.UserSvc.Role;
import edu.rutgers.cs336.services.UserSvc.User;

@Controller
@RequestMapping("/adminadd")
public class AdminAdd {
    @Autowired
    private UserSvc users;

    @GetMapping
    public String index(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));
        return "adminadd";
    }

    @PutMapping
    public String register(@ModelAttribute User form, HttpSession session, Model model) {
        if(form.role() == Role.REPRESENTATIVE)
        {
            users.registerRep(form).ifPresentOrElse(user -> {
                model.addAttribute("message", "Successfully registered user!");
    
            }, () -> {
                // User already exists
                model.addAttribute("message", "User already exists");
            });
        }
        else
        {
            users.register(form).ifPresentOrElse(user -> {
                model.addAttribute("message", "Successfully registered user!");
    
            }, () -> {
                // User already exists
                model.addAttribute("message", "User already exists");
            });
        }
        return index(session, model);
    }
}
