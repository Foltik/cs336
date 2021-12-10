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
@RequestMapping("representativeairportadd")
public class CustomerRepresentativeAddAirport {
    @Autowired
    private AirportSvc airports;

    @GetMapping
    public String index(HttpSession session, Model model){
        model.addAttribute("user", session.getAttribute("user"));
        return "representativeairportadd";
    }

    @PutMapping 
    public String addAirport(@ModelAttribute Airport airport, HttpSession session, Model model){
        Optional<Airport> airportExists = airports.findById(airport.id());
        if(airportExists.isPresent()){
            model.addAttribute("message", "Airport already exists with ID: " + airport.id());
        }
        else {
            airports.create(airport);
            model.addAttribute("message", "Airport added with ID: " + airport.id());
        }
    
        return index(session, model);
    }
}
