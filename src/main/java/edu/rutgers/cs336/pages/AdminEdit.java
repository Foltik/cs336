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
@RequestMapping("/adminedit")
public class AdminEdit {
    @Autowired
    private UserSvc users;

    @GetMapping
    public String index(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));
        return "adminedit";
    }
    
    @PutMapping
    public String Update(@ModelAttribute User form, HttpSession session, Model model)
    {

        
        Optional<User> orig = users.findById(form.id());
        if(orig.isPresent())
        {
            int id = form.id();
            String username = form.username();
            String password = form.password();
            String first_name = form.first_name();
            String last_name = form.last_name();
            Role role = form.role();

            if(username == "")
                username = orig.get().username();
            if(password == "")
                password = orig.get().password();
            if(first_name == "")
                first_name = orig.get().first_name();
            if(last_name == "")
                last_name = orig.get().last_name();
            if(role != Role.ADMIN && role != Role.REPRESENTATIVE && role != Role.CUSTOMER)
                role = orig.get().role();

            User usr = new User(id, username, password, first_name, last_name, role);
            //model.addAttribute("message", usr.toString());
            users.update(usr);
            model.addAttribute("message", "Updated user");
        }
        else
            model.addAttribute("message", "ID not found.");
        
        return index(session,model);
    }
}
