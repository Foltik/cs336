package edu.rutgers.cs336.pages;
import javax.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.rutgers.cs336.services.ReportSvc;
import edu.rutgers.cs336.services.ReportSvc.ReportReservations;
import edu.rutgers.cs336.services.UserSvc.User;

@Controller
@RequestMapping("/adminresbycust")
public class adminresbycust {
    @Autowired
    private ReportSvc rep;

    @GetMapping
    public String index(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));
        return "adminresbycust";
    }

    @PutMapping
    public String GetReservations(@ModelAttribute User form, HttpSession session, Model model)
    {
        List<ReportReservations> list = rep.GetReservationsByCust(form);
        model.addAttribute("list", list);
        return index(session, model);
    }
}
