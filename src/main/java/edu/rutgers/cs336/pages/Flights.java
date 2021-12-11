package edu.rutgers.cs336.pages;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.rutgers.cs336.services.AirportSvc;
import edu.rutgers.cs336.services.FlightSvc;

@Controller
@RequestMapping("/flights")
public class Flights {
    @Autowired
    private FlightSvc flights;

    @Autowired
    private AirportSvc airports;

    @GetMapping
    public String index (Model model) {
        model.addAttribute("flights", flights.index());
        model.addAttribute("seats", flights.index().stream()
            .collect(Collectors.toMap(f -> f.id(), f -> flights.getRemainingSeats(f))));
        model.addAttribute("airports", airports.index().stream()
            .collect(Collectors.toMap(a -> a.id(), a -> a)));
        return "flights";
    }
}
