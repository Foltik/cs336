package edu.rutgers.cs336.pages;
import javax.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.rutgers.cs336.services.ReportSvc;
import edu.rutgers.cs336.services.UserSvc;
import edu.rutgers.cs336.services.ReportSvc.TopFlights;
import edu.rutgers.cs336.services.UserSvc.User;

@Controller
@RequestMapping("/adminactiveflights")
public class AdminActiveFlights {
    @Autowired
    private ReportSvc rep;

    @GetMapping
    public String index(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));

        List<TopFlights> list = rep.index();
        model.addAttribute("list", list);
        return "adminactiveflights";
    }
    
}
