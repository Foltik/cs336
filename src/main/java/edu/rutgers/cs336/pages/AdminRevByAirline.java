package edu.rutgers.cs336.pages;
import javax.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.rutgers.cs336.services.ReportSvc;
import edu.rutgers.cs336.services.ReportSvc.GenericResult;
@Controller
@RequestMapping("/adminrevbyairline")
public class AdminRevByAirline {
    @Autowired
    private ReportSvc rep;

    @GetMapping
    public String index(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));

        List<GenericResult> list = rep.RevByAirlines();
        model.addAttribute("list", list);
        return "adminrevbyairline";
    }
    
}
