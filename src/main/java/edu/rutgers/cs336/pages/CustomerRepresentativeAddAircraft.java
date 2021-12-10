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

import edu.rutgers.cs336.services.AircraftSvc;
import edu.rutgers.cs336.services.AircraftSvc.Aircraft;


@Controller
@RequestMapping("/representativeaircraftadd")
public class CustomerRepresentativeAddAircraft {
    @Autowired 
    private AircraftSvc aircrafts;

    @GetMapping
    public String index(HttpSession session, Model model){
        model.addAttribute("user", session.getAttribute("user"));
        return "representativeaircraftadd";
    }

    @PutMapping
    public String addAircraft(@ModelAttribute Aircraft aircraft, HttpSession session, Model model){
        Optional<Aircraft> aircraftExists = aircrafts.findById(aircraft.id());
        if(aircraftExists.isPresent()){
            model.addAttribute("message", "Aircraft already exists with ID: " + aircraft.id());
        }
        else{
            aircrafts.create(aircraft);
            model.addAttribute("message", "Aircraft created with ID: " + aircraft.id());
        }
        
        return index(session, model);
    }
}
