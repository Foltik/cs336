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
@RequestMapping("/representativeaircraftupdate")
public class CustomerRepresentativeUpdateAircrafts {
    @Autowired
    private AircraftSvc aircrafts;

    @GetMapping
    private String index(HttpSession session, Model model){
        model.addAttribute("user", session.getAttribute("user"));
        return "representativeaircraftupdate";
    }

    @PutMapping
    public String updateAircraft(@ModelAttribute Aircraft aircraft, HttpSession session, Model models){
        Optional<Aircraft> aircraftExists = aircrafts.findById(aircraft.id());
        if(aircraftExists.isPresent()){

            int id = aircraft.id();
            int airline_id = aircraft.airline_id();
            String model = aircraft.model();
            int seats = aircraft.seats();
            
            if(airline_id == Integer.parseInt(""))
                airline_id = aircraftExists.get().airline_id();
            if(model == "")
                model = aircraftExists.get().model();
            if(seats == Integer.parseInt(""))
                seats = aircraftExists.get().seats();

            Aircraft newAircraft = new Aircraft(id, airline_id, model, seats);

            aircrafts.update(newAircraft);
            models.addAttribute("message", "Aircraft updated.");

        }else
            models.addAttribute("message", "Aircraft not found");

        return index(session, models);
    }

}
