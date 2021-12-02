package edu.rutgers.cs336.pages;
import javax.servlet.http.HttpSession;

import java.lang.StackWalker.Option;
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
@RequestMapping("/admindelete")
public class AdminDelete {
    @Autowired
    private UserSvc users;

    @GetMapping
    public String index(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));
        return "admindelete";
    }
    
    @PutMapping
    public String Delete(@ModelAttribute User form, HttpSession session, Model model)
    {
        
        Optional<User> usr = users.findById(form.id());
        if(usr.isPresent())
        {
            users.delete(form.id());
            model.addAttribute("message", "Deleted ID " + form.id());
        }
        else
        {
            model.addAttribute("message", "ID " + form.id() + " could not be found.");
        }
        return index(session, model);
    }
}
