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

import edu.rutgers.cs336.services.AirportSvc;
import edu.rutgers.cs336.services.AirportSvc.Airport;

@Controller
@RequestMapping("/representativeairportdelete")
public class CustomerRepresentativeDeleteAirport {
    @Autowired
    private AirportSvc airports;

    @GetMapping
    public String index(HttpSession session, Model model){
        model.addAttribute("user", session.getAttribute("user"));
        return "representativeairportdelete";
    }

    @PutMapping
    public String deleteAirport(@ModelAttribute Airport airport, HttpSession session, Model model){
        Optional<Airport> airportExists = airports.findById(airport.id());
        if(airportExists.isPresent()){
            airports.delete(airport.id());
            model.addAttribute("message", "Airport deleted with ID: " + airport.id());
        }
        else{
            model.addAttribute("message", "Airport could not be found with ID: " + airport.id());
        }
        return index(session, model);
    }
    
}
