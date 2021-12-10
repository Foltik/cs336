package edu.rutgers.cs336.pages;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.rutgers.cs336.services.FlightSvc;
import edu.rutgers.cs336.services.FlightSvc.Day;
import edu.rutgers.cs336.services.FlightSvc.Domain;
import edu.rutgers.cs336.services.FlightSvc.Flight;

@Controller
@RequestMapping("/representativeflightupdate")
public class CustomerRepresentativeUpdateFlight {
    @Autowired
    private FlightSvc flights;

    @GetMapping
    public String index(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));
        return "representativeflightupdate";
    }

    @PutMapping
    public String flightUpdate(@ModelAttribute Flight flight, HttpSession session, Model model){
        Optional<Flight> flightExists = flights.findById(flight.id());
        if(flightExists.isPresent()){

            int id = flight.id();
            int aircraft_id = flight.aircraft_id();
            int from_airport_id = flight.from_airport_id();
            int to_airport_id = flight.to_airport_id();
            LocalTime takeoff_time = flight.takeoff_time();
            LocalTime landing_time = flight.landing_time();
            Set<DayOfWeek> days = flight.days();
            Domain domain = flight.domain();
            float fare = flight.fare();

            if(aircraft_id == Integer.parseInt(""))
                aircraft_id = flightExists.get().aircraft_id();
            if(from_airport_id == Integer.parseInt(""))
                from_airport_id = flightExists.get().from_airport_id();
            if(to_airport_id == Integer.parseInt(""))
                to_airport_id = flightExists.get().to_airport_id();
            if(takeoff_time == null)
                takeoff_time = flightExists.get().takeoff_time();
            if(landing_time == null)
                landing_time = flightExists.get().landing_time();
            if(days == null)
                days = flightExists.get().days();
            if(domain != Domain.DOMESTIC && domain != Domain.INTERNATIONAL)
                domain = flightExists.get().domain();
            if(fare == Float.parseFloat(""))
                fare = flightExists.get().fare();
            
            Flight newFlight = new Flight(id, aircraft_id, from_airport_id, to_airport_id, takeoff_time, landing_time, days, domain, fare);
            flights.update(newFlight);
            model.addAttribute("message", "Updated flight.");
        }
        else
            model.addAttribute("message", "No flight found");
        
            return index(session, model);
    }
}
