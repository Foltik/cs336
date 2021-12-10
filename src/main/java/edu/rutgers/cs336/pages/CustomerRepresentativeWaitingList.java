package edu.rutgers.cs336.pages;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.rutgers.cs336.services.BookingSvc;
import edu.rutgers.cs336.services.FlightSvc;
import edu.rutgers.cs336.services.FlightSvc.Flight;
import edu.rutgers.cs336.services.UserSvc.User;

@Controller
@RequestMapping("/representativepasswaitinglist")
public class CustomerRepresentativeWaitingList {
    @Autowired
    private BookingSvc booking;

    @Autowired
    private FlightSvc flights;

    @GetMapping
    public String index(HttpSession session, Model model, Flight flight) {
        model.addAttribute("user", session.getAttribute("user"));

        List<User> list = booking.getWaitingCustomers(flight);
        model.addAttribute("list", list);
        return "representativeflightlist";
    }
    
}
    
