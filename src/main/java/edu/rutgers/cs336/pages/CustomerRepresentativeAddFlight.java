package edu.rutgers.cs336.pages;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.rutgers.cs336.services.FlightSvc;
import edu.rutgers.cs336.services.FlightSvc.Flight;

@Controller
@RequestMapping("/representativeflightadd")
public class CustomerRepresentativeAddFlight {
    @Autowired
    private FlightSvc flights;

    @GetMapping
    public String index(HttpSession session, Model model){
        model.addAttribute("user", session.getAttribute("user"));
        return "representativeflightadd";
    }

    @PutMapping
    public String addFlight(@ModelAttribute Flight flight, HttpSession session, Model model){
        Optional<Flight> flightExists = flights.findById(flight.id());
        if(flightExists.isPresent()){
            model.addAttribute("message", "Flight already exists with ID." + flight.id());
        }
        else{
            flights.create(flight);
            model.addAttribute("message", "Flight registered with ID." + flight.id());
        }
        return index(session, model);
    }

}
