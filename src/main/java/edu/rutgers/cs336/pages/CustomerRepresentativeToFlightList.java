package edu.rutgers.cs336.pages;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.rutgers.cs336.services.AirportSvc;
import edu.rutgers.cs336.services.FlightSvc;
import edu.rutgers.cs336.services.AirportSvc.Airport;
import edu.rutgers.cs336.services.FlightSvc.Flight;

@Controller
@RequestMapping("/representativetoflightlist")
public class CustomerRepresentativeToFlightList {
    @Autowired 
    FlightSvc flights;
    
    @Autowired
    AirportSvc airports;

    @GetMapping
    public String index(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));
        return "representativetoflightlist";
    }

    @PutMapping 
    String getToFlightList(@ModelAttribute Flight flight, HttpSession session, Model model){
        List<Flight> list = flights.getToAirport(flight.to_airport_id());
        model.addAttribute("list", list);
        return index(session, model);
    }
}
