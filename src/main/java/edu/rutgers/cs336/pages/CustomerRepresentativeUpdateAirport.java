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
@RequestMapping("/representativeairportupdate")
public class CustomerRepresentativeUpdateAirport {
    @Autowired
    private AirportSvc airports;

    @GetMapping
    public String index(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));
        return "representativeairportupdate";
    }

    @PutMapping
    public String updateAirport(@ModelAttribute Airport airport, HttpSession session, Model model){
        Optional<Airport> airportExists = airports.findById(airport.id());

        if(airportExists.isPresent()){
            int id = airport.id();
            String name = airport.name();

            if(name == "")
                name = airportExists.get().name();
            
                Airport newAirport = new Airport(id, name);
                airports.update(newAirport);
                model.addAttribute("message", "Airport updated.");
        }
        else
            model.addAttribute("message", "ID not found.");
        
            return index(session,model);
    }
}
