package edu.rutgers.cs336.pages;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.rutgers.cs336.services.ReportSvc;
import edu.rutgers.cs336.services.UserSvc;
import edu.rutgers.cs336.services.UserSvc.User;
import edu.rutgers.cs336.services.ReportSvc.GenericResult;

@Controller
@RequestMapping("/admintopcustomer")
public class AdminTopCustomer {
    @Autowired
    private ReportSvc rep;

    @Autowired
    private UserSvc users;

    @GetMapping
    public String index(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));


        Optional<GenericResult> top = rep.TopCustomer();
        if(top.isPresent())
        {
            Optional<User> usr = users.findById(top.get().id());
            model.addAttribute("customer", usr.get());
            model.addAttribute("revenue", top.get().count());
        }
        return "admintopcustomer";
    }
    
}