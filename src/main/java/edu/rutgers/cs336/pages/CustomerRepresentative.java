package edu.rutgers.cs336.pages;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.rutgers.cs336.services.UserSvc;
import edu.rutgers.cs336.services.UserSvc.User;

@Controller
@RequestMapping("/customerrep")
public class CustomerRepresentative {
    @Autowired
    private UserSvc users;

    @GetMapping
    public String index(HttpSession session, Model model){
        model.addAttribute("user", session.getAttribute("user"));

        List<User> list = users.index();
        model.addAttribute("list", list);
        return "customerrep";
    }
}
