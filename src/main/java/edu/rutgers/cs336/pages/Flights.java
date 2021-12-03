package edu.rutgers.cs336.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.rutgers.cs336.services.FlightSvc;
import edu.rutgers.cs336.services.FlightSvc.Flight;

@Controller
@RequestMapping("/flights")
public class Flights {
    @Autowired
    private FlightSvc flights;

    @GetMapping
    public String index (Model model) {
        model.addAttribute("flights", flights.index());
        return "flights";
    }

    @PostMapping
    public String create(@ModelAttribute Flight flight, Model model) {
        flights.create(flight);
        return index(model);
    }

    @PutMapping
    public String update(@ModelAttribute Flight flight, Model model) {
        flights.update(flight);
        return index(model);
    }

    @DeleteMapping
    public String delete(@ModelAttribute Flight flight, Model model) {
        flights.delete(flight.id());
        return index(model);
    }
}
